package com.protops.gateway.controller.refresh;

import com.protops.gateway.constants.AdminConfig;
import com.protops.gateway.constants.WeixinConfig;
import com.protops.gateway.domain.Config;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by fanlin on 15/4/18.
 */
@Controller
@RequestMapping(value = "config")
public class ConfigRefreshController {

    @Autowired
    ConfigService configService;

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse refresh() {

        AjaxResponse ajaxResponse = new AjaxResponse();

        List<Config> configList = configService.refresh(2);

        AdminConfig.refresh(configList);

        ajaxResponse.setMsg("refresh gateway config");

        return ajaxResponse;
    }
}
