package com.protops.gateway.controller.weixin;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.protops.gateway.Job.AreaJob;
import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.constants.enums.MemberStatus;
import com.protops.gateway.constants.enums.Page;
import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.domain.ChargePolicy;
import com.protops.gateway.domain.Member;
import com.protops.gateway.domain.ajax.AjaxRequest;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.service.AreaService;
import com.protops.gateway.service.LocationService;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.weixin.ChargePolicyService;
import com.protops.gateway.util.NumberGenerator;
import com.protops.gateway.utils.ResponseCodeHelper;
import com.protops.gateway.vo.lbs.H5AreaDetail;
import com.protops.gateway.vo.lbs.LBSVo;
import com.protops.gateway.vo.lbs.ReportLocationRequest;
import com.protops.gateway.vo.lbs.ReportLocationResponse;
import com.protops.gateway.vo.member.RegisterRequest;
import com.protops.gateway.vo.member.RegisterResponse;
import com.protops.gateway.weixin.WeixinSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouzhihao on 2014/10/31.
 */
@Controller
@RequestMapping(value = "weixin/h5")
public class LocationController extends WeixinBaseController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    LocationService locationService;

    @Autowired
    AreaService areaService;

    @Autowired
    SensorService sensorService;

    @Autowired
    AreaJob areaJob;

    @Autowired
    ChargePolicyService chargePolicyService;

    @Autowired
    WeixinConfig weixinConfig;

    private void enableWeixinJs(Model model) {
        String ticket = weixinConfig.getTicket();

        String noncestr = NumberGenerator.generate();
        String time = String.valueOf(new Date().getTime());

        List<String> list = new ArrayList<String>();
        list.add(("noncestr=" + noncestr + "&"));
        list.add(("jsapi_ticket=" + ticket + "&"));
        list.add(("timestamp=" + time + "&"));
        list.add(("url=" + "http://wx.zhanway.com/gtw/weixin/h5/nearby"));
        Collections.sort(list);

        String result = StringUtils.join(list, "");

        String sign = Hashing.sha1().hashString(result, Charsets.UTF_8).toString();

        model.addAttribute("nonceStr", noncestr);
        model.addAttribute("timestamp", time);
        model.addAttribute("jsapi_ticket", ticket);
        model.addAttribute("url", "http://wx.zhanway.com/gtw/weixin/h5/nearby");
        model.addAttribute("sign", sign);
        model.addAttribute("appId", weixinConfig.getAppId());

        model.addAttribute("ak", "F2422682c3c379bf1ea8857993588bb8");
    }

    @RequestMapping(value = "nearby", method = RequestMethod.GET)
    public String nearby(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        enableWeixinJs(model);




        return "weixin/nearby";

    }

    @RequestMapping(value = "getAreasByDistrict", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResponse<ReportLocationResponse> login(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestBody AjaxRequest<ReportLocationRequest> weixinRequest, Model model) throws Exception {

        AjaxResponse<ReportLocationResponse> weixinResponse = new AjaxResponse<ReportLocationResponse>();

        ReportLocationRequest reportLocationRequest = weixinRequest.getDomain();

        System.out.println(reportLocationRequest.getDistrict());

        // 获取
        List<Area> areaList = areaService.getByDistrict(URLDecoder.decode(reportLocationRequest.getDistrict(), Constants.Default_SysEncoding));

        System.out.println(areaList.size());

        if(areaList == null || areaList.size() == 0){
            weixinResponse.setDomain(new ReportLocationResponse(new ArrayList<AreaVo>()));
            return weixinResponse;
        }

        List<AreaVo> areaVoList = sensorService.getByAreaId(areaList);
        if (areaVoList == null || areaVoList.size() == 0) {
            areaVoList = new ArrayList<AreaVo>();
            for (Area area : areaList) {
                areaVoList.add(new AreaVo(area));
            }
        }

        weixinResponse.setDomain(new ReportLocationResponse(areaVoList));

        return weixinResponse;

    }

    /**
     * @param weixinSession
     * @param weixinCode
     * @param model
     * @return
     * @throws Exception localhost:8080/gtw/service/myMemberCard
     */
    @RequestMapping(value = "location", method = RequestMethod.GET)
    public String login(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        List<Location> locationList = locationService.list();

        List<LBSVo> lbsVos = new ArrayList<LBSVo>();

        for (Location location : locationList) {

            List<Area> areaList = areaService.getAreasByLocationId(location.getId());

            List<AreaVo> areaVoList = sensorService.getByAreaId(areaList);
            if (areaVoList == null || areaVoList.size() == 0) {
                areaVoList = new ArrayList<AreaVo>();
                for (Area area : areaList) {
                    areaVoList.add(new AreaVo(area));
                }
            }

            LBSVo lbsVo = new LBSVo(location, areaVoList);

            lbsVos.add(lbsVo);

        }

        model.addAttribute("lbsList", lbsVos);
        model.addAttribute("page", Page.location);

        return "weixin/location";

    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(@RequestParam(required = true, value = "id") Integer areaId, @ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {

        AreaVo areaVo = sensorService.getUniqueByAreaId(areaId);

        // 如果找不到这条记录，就获取area的记录以便处理
        if (areaVo == null) {
            Area area = areaService.get(areaId);
            areaVo = new AreaVo(area);
        }

        BigInteger totalCnt = areaJob.getCnt(areaVo.getId());
        areaVo.setTotalCnt(totalCnt);

        ChargePolicy chargePolicy = chargePolicyService.get(areaVo.getChargePolicyId());

        H5AreaDetail h5AreaDetail = new H5AreaDetail(areaVo, chargePolicy);

        model.addAttribute("detail", h5AreaDetail);

        return "weixin/detail";

    }
}
