package com.protops.gateway.controller.common;

import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.service.AppInfoService;
import com.protops.gateway.service.LocationService;
import com.protops.gateway.service.common.TokenFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by jinx on 3/27/17.
 */
@Controller
public class CommonLocationRestController {

    private static final Logger logger = LoggerFactory.getLogger(CommonLocationRestController.class);

    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private TokenFactoryService tokenFactoryService;


    /**
     * 第三方获取location列表
     * @param token
     * @return
     */
    @RequestMapping(value = "rest/locations")
    @ResponseBody
    public AjaxResponse locations(@RequestParam(value = "token", required = false) String token) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("[req:{}]", token);
        if (tokenFactoryService.checkToken(token)) {
            TokenFactory tokenFactory = tokenFactoryService.getToken(token);
            AppInfo appInfo = appInfoService.getByAppId(tokenFactory.getBaseId());
            List<Location> list = locationService.findByAppInfoId(appInfo.getId());
            ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, list);
        } else {
            ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
        }
        return ajaxResponse;
    }

    /**
     * 第三方修改location信息
     * @param token
     * @param name
     * @param desc
     * @param id
     * @return
     */
    @RequestMapping(value = "rest/location/update")
    @ResponseBody
    public AjaxResponse locationUpdate(@RequestParam(value = "token", required = false) String token,
                                       @RequestParam(value = "name",required = false) String name,
                                       @RequestParam(value = "desc",required = false) String desc,
                                       @RequestParam(value = "id",required = false) Integer id) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            logger.warn(" [req:{}]", token);
            if (tokenFactoryService.checkToken(token)) {
                Location location = locationService.get(id);
                location.setName(name);
                location.setDesc(desc);
                locationService.update(location);
                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, null);
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        }catch (Exception e){
            logger.error("e:{}",e);
        }
        return ajaxResponse;
    }


}
