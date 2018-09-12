package com.protops.gateway.controller.weixin;

import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.domain.ajax.AjaxRequest;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.exception.WeixinException;
import com.protops.gateway.service.PunishService;
import com.protops.gateway.utils.ResponseCodeHelper;
import com.protops.gateway.vo.punish.PunishRequest;
import com.protops.gateway.vo.punish.PunishResponse;
import com.protops.gateway.weixin.WeixinSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by damen on 2016/6/20.
 */
@Controller
@RequestMapping(value = "weixin/h5")
public class PunishController extends WeixinBaseController {

    @Autowired
    PunishService punishService;


    @RequestMapping(value = "punish", method = RequestMethod.GET)
    public String punish(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestParam(value = "code", required = false) String weixinCode, Model model) throws Exception {
        return "weixin/punish";
    }


    @RequestMapping(value = "check", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResponse<PunishResponse> check(@ModelAttribute("weixinSession") WeixinSession weixinSession, @RequestBody AjaxRequest<PunishRequest> weixinRequest, Model model) throws Exception {

        AjaxResponse<PunishResponse> weixinResponse = new AjaxResponse<PunishResponse>();

        try {

            PunishRequest punishRequest = weixinRequest.getDomain();

            String plateNumber = punishRequest.getPlateNumber();

            if (StringUtils.isBlank(plateNumber)) {
                throw new WeixinException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

//            Punish punish = punishService.getByPlateNumber(plateNumber);


        } catch (WeixinException e) {

            model.addAttribute("errMsg", ResponseCodeHelper.parseMessage(e));

        }
        return weixinResponse;

    }
}
