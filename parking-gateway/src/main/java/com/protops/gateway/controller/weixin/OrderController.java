package com.protops.gateway.controller.weixin;

import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.constants.enums.OrderStatus;
import com.protops.gateway.constants.enums.OrderType;
import com.protops.gateway.constants.enums.ParkingLotStatus;
import com.protops.gateway.constants.enums.QueryResult;
import com.protops.gateway.controller.client.WeixinPaymentClient;
import com.protops.gateway.domain.*;
import com.protops.gateway.domain.ajax.AjaxRequest;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.service.AreaService;
import com.protops.gateway.service.ParkingFLowService;
import com.protops.gateway.service.SmsService;
import com.protops.gateway.service.weixin.ChargePolicyService;
import com.protops.gateway.service.weixin.MemberService;
import com.protops.gateway.service.weixin.OrderService;
import com.protops.gateway.service.weixin.ParkingLotService;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.NumberGenerator;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.utils.ResponseCodeHelper;
import com.protops.gateway.vo.order.*;
import com.protops.gateway.weixin.WeixinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by zhouzhihao on 2014/10/31.
 */
@Controller
@RequestMapping(value = "weixin/h5")
public class OrderController extends WeixinBaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    MemberService memberService;

    @Autowired
    SmsService smsService;

    @Autowired
    OrderService orderService;

    @Autowired
    WeixinPaymentClient weixinClient;

    @Autowired
    WeixinConfig weixinConfig;

    @Autowired
    ParkingLotService parkingLotService;

    @Autowired
    AreaService areaService;

    @Autowired
    ChargePolicyService chargePolicyService;

    @Autowired
    ParkingFLowService parkingFlowService;

    @RequestMapping(value = "payment", method = RequestMethod.GET)
    public String payment(@RequestParam(value = "lotNo", required = false) String lotNo, @ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        if(StringUtils.isNotBlank(lotNo)){

            model.addAttribute("lotNo", lotNo);

        }else{

            ParkingLot parkingLot = parkingLotService.getParkingLotByCurMemberId(weixinSession.getMember().getId());

            if(parkingLot != null){

                return "redirect:current";

            }
        }

        return "weixin/payment";

    }

    @RequestMapping(value = "validateLot", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResponse<ValidateLotResponse> validateLot(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestBody AjaxRequest<ValidateLotRequest> weixinRequest) throws Exception {

        AjaxResponse<ValidateLotResponse> response = new AjaxResponse<ValidateLotResponse>();

        try {

            /**
             * 1，检查泊位是否合法
             * 2，检查泊位是否有车
             * 3，检查泊位是否正确
             */
            ValidateLotRequest validateLotRequest = weixinRequest.getDomain();

            String lotNo = validateLotRequest.getLotNo();

            if (StringUtils.isBlank(lotNo)) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_NO_ILLEGAL);
            }

            ParkingLot parkingLot = parkingLotService.getByLotNo(lotNo);

            if (parkingLot == null) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            if (!parkingLot.allowPay()) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            Area area = areaService.get(parkingLot.getAreaId());
            if (area == null) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            ChargePolicy chargePolicy = chargePolicyService.get(area.getChargePolicyId());

            if (chargePolicy == null) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            ChargePolicyVo chargePolicyVo = new ChargePolicyVo(chargePolicy);

            ValidateLotResponse validateLotResponse = new ValidateLotResponse();
            validateLotResponse.setChargePolicy(chargePolicyVo);
            validateLotResponse.setParkingLot(parkingLot.getLotNo());

            response.setDomain(validateLotResponse);

        } catch (WeixinException e) {

            logger.warn("validateLot", e);

            ResponseCodeHelper.parseErrorResponse(response, e);

        }

        logger.warn("[validateLotResponse][{}]", JsonUtil.toJson(response));

        return response;

    }


    /**
     * @param weixinSession
     * @return
     * @throws Exception localhost:8080/gtw/service/myMemberCard
     */
    @RequestMapping(value = "pushOrder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResponse<PushOrderResponse> pushOrder(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestBody AjaxRequest<PushOrderRequest> weixinRequest) throws Exception {

        AjaxResponse<PushOrderResponse> response = new AjaxResponse<PushOrderResponse>();

        try {

            PushOrderRequest pushOrderRequest = weixinRequest.getDomain();

            String lotNo = pushOrderRequest.getParkingLot();

            if (StringUtils.isBlank(lotNo)) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_NO_ILLEGAL);
            }

            ParkingLot parkingLot = parkingLotService.getByLotNo(lotNo);

            if (parkingLot == null) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            if (!parkingLot.allowPay()) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            Area area = areaService.get(parkingLot.getAreaId());
            if (area == null) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            ChargePolicy chargePolicy = chargePolicyService.get(area.getChargePolicyId());

            if (chargePolicy == null) {
                throw new WeixinException(ResponseCodes.Weixin.LOT_UNAVAILABLE);
            }

            Integer timespan = pushOrderRequest.getTimespan();

            Integer totalAmount = chargePolicy.getFirstHour() + (timespan - 60) / 30 * chargePolicy.getRemainHalfHour();

            Order order = new Order();
            order.setOrderNumber(NumberGenerator.generate());
            order.setMemberId(weixinSession.getMember().getId());
//            order.setMemberId(1);
            order.setOrderTime(new Date());
            order.setTotalAmount(totalAmount);
            order.setStatus(OrderStatus.unpaid.getStatus());
            if(pushOrderRequest.isRenewal()){
                order.setOrderType(OrderType.xufei.getStatus());
                Date expireTime = DateUtil.getAddedTime(parkingLot.getExpireTime(), Calendar.MINUTE, timespan);
                order.setExpireTime(expireTime);
            }else{
                order.setOrderType(OrderType.payment.getStatus());
                Date expireTime = DateUtil.getAddedTime(parkingLot.getInTime(), Calendar.MINUTE, timespan);
                order.setExpireTime(expireTime);
            }

            OrderAttach orderAttach = new OrderAttach();
            orderAttach.setParkingLot(lotNo);
            orderAttach.setAreaId(area.getId());
            orderAttach.setParkingTimeSpan(timespan);
            orderAttach.setMemberId(weixinSession.getMember().getId());
//            orderAttach.setMemberId(1);
            order.setAttach(orderAttach);
            order.setReserved(orderAttach.toBase64());

            // 如果正常，就推订单给微信
            String prepayId = weixinClient.pushOrder(weixinSession.getOpenId(), order, weixinConfig);
//            String prepayId = NumberGenerator.generate(); // //TODO MOCK
            if (StringUtils.isEmpty(prepayId)) {
                throw new WeixinException(ResponseCodes.Weixin.PUSH_ORDER_TO_WEIXIN_FAILED);
            }

            // save order
            orderService.save(order);

            H5Prepay h5Prepay = new H5Prepay(weixinConfig.getAppId(), prepayId);

            h5Prepay.sign(weixinConfig.getKey());

            PushOrderResponse pushOrderResponse = new PushOrderResponse();
            pushOrderResponse.setOrderNumber(order.getOrderNumber());
            pushOrderResponse.setH5Prepay(h5Prepay);

            response.setDomain(pushOrderResponse);

        } catch (WeixinException e) {

            logger.warn("pushOrder", e);

            ResponseCodeHelper.parseErrorResponse(response, e);

        }

        logger.warn("[pushOrderResponse][{}]", JsonUtil.toJson(response));

        return response;
    }

    /**
     * 该接口反复查询本地订单状态。只要没有成功，都直接返回正在处理
     *
     * @param orderNumber
     * @param weixinSession
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public QueryResult query(@RequestParam(value = "orderNumber", required = false) String orderNumber, @ModelAttribute("weixinSession") WeixinSession weixinSession, Model model) throws Exception {

        Order order = getCurrentOrder(orderNumber, weixinSession, false);

        // 只要是未支付，都是正在处理
        if (order.getStatus() == Order.ORDER_STATUS_UNPAID) {
            return QueryResult.InProcess;
        }

        // 其他情况，都是成功
        return QueryResult.Succeed;

//
//        // 只有订单状态是未支付，才会发起查询。此时必定还没有收到支付结果通知。
//        OrderQueryResponse orderQueryResponse = weixinClient.query(order, weixinConfig);
//
//        WeixinTradeState weixinTradeState = WeixinTradeState.valueOf(orderQueryResponse.getTrade_state());
//
//        if (weixinTradeState == null) {
//            return QueryResult.Failed;
//        }
//
//        QueryResult queryResult = QueryResult.parse(weixinTradeState.getInnerQueryResult());
//
//        if (queryResult == null) {
//            return QueryResult.Failed;
//        }

        // 不执行下面的逻辑，以确保订单仅以后台通知为准；
        // 如果查询结果是成功的，那么进入成功结果流程
//        if (queryResult == QueryResult.Succeed) {
//
//            orderService.notifyOk(order, orderQueryResponse.getTransaction_id());
//
//            Attach attach = Attach.toAttach(orderQueryResponse.getAttach());
//
//            followUpProcess(order, attach);
//
//        }

    }

    private Order getCurrentOrder(String orderNumber, WeixinSession weixinSession, boolean amountCheck) throws WeixinException {

        if (StringUtils.isEmpty(orderNumber)) {
            throw new WeixinException(ResponseCodes.Sys.SYS_ERR);
        }

        return orderService.getByOrderNumber(orderNumber);
    }

    @RequestMapping(value = "failed", method = RequestMethod.GET)
    public String orderResult(@RequestParam(value = "ret", required = false) Integer result, @RequestParam(value = "orderNumber", required = false) String orderNumber, @ModelAttribute("weixinSession") WeixinSession weixinSession, Model model) throws Exception {

        model.addAttribute("result", result);
        model.addAttribute("orderNumber", orderNumber);

        return "weixin/failed";
    }

    @RequestMapping(value = "notify", method = RequestMethod.POST)
    @ResponseBody
    public String notify(@RequestBody String request) throws Exception {

        NotifyRequest notifyRequest = weixinClient.notify(request, weixinConfig);

        if (notifyRequest == null) {
            return NotifyResponse.failedMsg();
        }

        /*
        从这里开始，就表示微信支付已经成功，如果抓到异常，就说明自身逻辑出现问题
        1，解开attach失败？
        2，插入order表失败？
        3，插入payHistory失败？
        4，获取vihicleInOutFlow失败？
        5，通知停车场失败？
         */

        Order order = orderService.getByOrderNumber(notifyRequest.getOut_trade_no());

        // 不管是后台通知还是前台通知，只要订单已经支付过，就返回成功
        if (order.getStatus() == OrderStatus.paid.getStatus()) {
            return NotifyResponse.OkMsg();
        }

        // 更新订单状态
        orderService.notifyOk(order, notifyRequest.getTransaction_id());

        OrderAttach orderAttach = (OrderAttach) Attach.toAttach(notifyRequest.getAttach());

        // 支付成功之后更新到期时间，如果是续费，要更新成较新的值
        ParkingLot parkingLot = parkingLotService.getByLotNo(orderAttach.getParkingLot());

        parkingLot.setExpireTime(order.getExpireTime());
        parkingLot.setPaid(ParkingLotStatus.paid.getId());
        parkingLot.setCurrentMemberId(orderAttach.getMemberId());
        parkingLotService.save(parkingLot);

        // flow表更新停车时间
        ParkingFlow parkingFlow = new ParkingFlow();
        parkingFlow.setMemberId(order.getMemberId());
        parkingFlow.setAreaId(orderAttach.getAreaId());
        parkingFlow.setLotNo(orderAttach.getParkingLot());
        parkingFlow.setParkingTimespam(orderAttach.getParkingTimeSpan());
        parkingFlow.setParkingTime(parkingLot.getInTime());

        parkingFlowService.save(parkingFlow);

        return NotifyResponse.OkMsg();
    }

    private void followProcess(Order order, String transId, OrderAttach orderAttach){

        // 更新订单状态
        orderService.notifyOk(order, transId);

        // 支付成功之后更新到期时间，如果是续费，要更新成较新的值
        ParkingLot parkingLot = parkingLotService.getByLotNo(orderAttach.getParkingLot());

        parkingLot.setExpireTime(order.getExpireTime());
        parkingLot.setPaid(ParkingLotStatus.paid.getId());
        parkingLot.setCurrentMemberId(orderAttach.getMemberId());
        parkingLotService.save(parkingLot);


        // flow表更新停车时间
        ParkingFlow parkingFlow = new ParkingFlow();
        parkingFlow.setMemberId(order.getMemberId());
        parkingFlow.setAreaId(orderAttach.getAreaId());
        parkingFlow.setLotNo(orderAttach.getParkingLot());
        parkingFlow.setParkingTimespam(orderAttach.getParkingTimeSpan());
        parkingFlow.setParkingTime(parkingLot.getInTime());

        parkingFlowService.save(parkingFlow);
    }

}
