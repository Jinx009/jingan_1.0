package com.protops.gateway.controller.weixin;

import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.MemberStatus;
import com.protops.gateway.constants.enums.ParkingLotStatus;
import com.protops.gateway.domain.Member;
import com.protops.gateway.domain.Order;
import com.protops.gateway.domain.OrderAttach;
import com.protops.gateway.domain.ParkingLot;
import com.protops.gateway.domain.ajax.AjaxRequest;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.service.AreaService;
import com.protops.gateway.service.ParkingFLowService;
import com.protops.gateway.service.SmsService;
import com.protops.gateway.service.weixin.MemberService;
import com.protops.gateway.service.weixin.OrderService;
import com.protops.gateway.service.weixin.ParkingLotService;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.utils.AmountUtil;
import com.protops.gateway.utils.ResponseCodeHelper;
import com.protops.gateway.vo.CurrentVo;
import com.protops.gateway.vo.member.RegisterRequest;
import com.protops.gateway.vo.member.RegisterResponse;
import com.protops.gateway.vo.order.OrderVo;
import com.protops.gateway.vo.order.ParkingFlowVo;
import com.protops.gateway.vo.sms.SendSmsRequest;
import com.protops.gateway.vo.sms.SendSmsResponse;
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
public class MemberController extends WeixinBaseController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    MemberService memberService;

    @Autowired
    SmsService smsService;

    @Autowired
    ParkingFLowService parkingFlowService;

    @Autowired
    OrderService orderService;

    @Autowired
    AreaService areaService;

    @Autowired
    ParkingLotService parkingLotService;


    @RequestMapping(value = "member", method = RequestMethod.GET)
    public String member(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        // 找到用户最近的订单，怎么找
        Member member = weixinSession.getMember();

        model.addAttribute("member", member);
        model.addAttribute("headImgUrl", weixinSession.getHeadImgUrl());

        return "weixin/member";

    }

    /**
     * 如果用户当前有最近的订单
     * 那么就获取最近的订单，必须是没有完成的
     *
     * 这里要获取停上去的时间（只能找到parkingLot）
     * @param weixinSession
     * @param weixinCode
     * @param model
     * @return
     * @throws Exception
     *
     * 这里要判断parkingLot的状态
     */
    @RequestMapping(value = "current", method = RequestMethod.GET)
    public String current(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        // 找到用户最近的订单，怎么找
        Member member = weixinSession.getMember();

        ParkingLot parkingLot = parkingLotService.getParkingLotByCurMemberId(member.getId());

        if(parkingLot == null){
            return "redirect:payment";
        }

        Area area = areaService.get(parkingLot.getAreaId());

        if(area == null){
            return "redirect:payment";
        }

        CurrentVo currentVo = new CurrentVo();
        currentVo.setAreaName(area.getName());
        currentVo.setLotNo(parkingLot.getLotNo());
        currentVo.setDate(DateUtil.getDate(parkingLot.getInTime(), DateUtil.DATE_FMT_DISPLAY2));

        currentVo.setStartTime(DateUtil.getDate(parkingLot.getInTime(), DateUtil.DATE_FMT_DISPLAY7));

        currentVo.setEndTime(DateUtil.getDate(parkingLot.getExpireTime(), DateUtil.DATE_FMT_DISPLAY7));

        // 这里是剩余时间：有车时间+停车时间-当前时间，即剩余时间
        Long remainingTime = (parkingLot.getExpireTime().getTime() - new Date().getTime()) / 1000;

        if(remainingTime <= 0 || ParkingLotStatus.expired.getId() == parkingLot.getPaid()){
            model.addAttribute("showTips", "true");
        }

        currentVo.setParkTimespan(remainingTime.intValue());

        model.addAttribute("current", currentVo);

        return "weixin/current";

    }

    /**
     * @param weixinSession
     * @param weixinCode
     * @param model
     * @return
     * @throws Exception localhost:8080/gtw/service/myMemberCard
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String login(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        return "weixin/register";

    }

    @RequestMapping(value = "register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResponse<RegisterResponse> login(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestBody AjaxRequest<RegisterRequest> weixinRequest, Model model) throws Exception {

        AjaxResponse<RegisterResponse> weixinResponse = new AjaxResponse<RegisterResponse>();

        try {

            RegisterRequest registerRequest = weixinRequest.getDomain();

            boolean ok = smsService.validateSms(registerRequest.getSmsCode(), weixinSession);

            if (!ok) {
                throw new WeixinException(ResponseCodes.Weixin.SMS_CODE_ERR);
            }

            Member member = memberService.getByMobile(registerRequest.getMobile());

            if (member != null) {
                throw new WeixinException(ResponseCodes.Weixin.USER_DUPLICATED);
            }

            member = new Member();
            member.setMobile(registerRequest.getMobile());
            member.setWeixinId(weixinSession.getOpenId());
            member.setStatus(MemberStatus.OK.getId());

            memberService.save(member);

            smsService.clearSentCnt(weixinSession);

        } catch (WeixinException e) {

            model.addAttribute("errMsg", ResponseCodeHelper.parseMessage(e));

        }
        return weixinResponse;

    }

    @RequestMapping(value = "sendSms", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResponse sendSms(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestBody AjaxRequest<SendSmsRequest> weixinRequest) throws Exception {

        logger.warn("[sendSmsRequest][{}]", JsonUtil.toJson(weixinRequest));

        AjaxResponse<SendSmsResponse> ajaxResponse = new AjaxResponse<SendSmsResponse>();

        try {

            SendSmsRequest sendSmsRequest = weixinRequest.getDomain();

            sendSmsRequest.validate();

            if (weixinSession == null) {
                throw new WeixinException(ResponseCodes.Weixin.SESSION_MISSING);
            }

            boolean ok = smsService.sendSms(sendSmsRequest.getMobile(), weixinSession);

            if (!ok) {

                throw new WeixinException(ResponseCodes.Weixin.SMS_SEND_ERROR);

            }

        } catch (WeixinException e) {

            logger.warn("sms send in exception", e);

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        logger.warn("[sendSmsResponse][{}]", JsonUtil.toJson(ajaxResponse));

        return ajaxResponse;
    }

    @RequestMapping(value = "parkingFlow", method = RequestMethod.GET)
    public String parkingFlow(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        List<ParkingFlowVo> parkingFlowList = parkingFlowService.getRecentByMemberId(weixinSession.getMember().getId());
//        List<ParkingFlowVo> parkingFlowList = parkingFlowService.getRecentByMemberId(1);

        Map<String, List<ParkingFlowVo>> parkingFlowMap = new HashMap<String, List<ParkingFlowVo>>();

        for (ParkingFlowVo parkingFlowVo : parkingFlowList) {

            if (parkingFlowMap.containsKey(parkingFlowVo.getDate())) {
                List<ParkingFlowVo> list = parkingFlowMap.get(parkingFlowVo.getDate());
                list.add(parkingFlowVo);
            } else {
                List<ParkingFlowVo> list = new ArrayList<ParkingFlowVo>();
                list.add(parkingFlowVo);
                parkingFlowMap.put(parkingFlowVo.getDate(), list);
            }
        }

        model.addAttribute("ret", parkingFlowMap);

        return "weixin/parkingFlow";

    }

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public String orders(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        List<OrderVo> orderVoList = orderService.getByMemberId(weixinSession.getMember().getId());
//        List<OrderVo> orderVoList = orderService.getByMemberId(1);

        Map<String, List<OrderVo>> orderVoMap = new HashMap<String, List<OrderVo>>();

        for (OrderVo orderVo : orderVoList) {

            if (orderVoMap.containsKey(orderVo.getDate())) {
                List<OrderVo> list = orderVoMap.get(orderVo.getDate());
                list.add(orderVo);
            } else {
                List<OrderVo> list = new ArrayList<OrderVo>();
                list.add(orderVo);
                orderVoMap.put(orderVo.getDate(), list);
            }
        }

        model.addAttribute("ret", orderVoMap);

        return "weixin/order";

    }

}
