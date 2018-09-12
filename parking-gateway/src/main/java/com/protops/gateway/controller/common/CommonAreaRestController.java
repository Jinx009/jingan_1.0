package com.protops.gateway.controller.common;

import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.service.AreaService;
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
 * Created by jinx on 3/28/17.
 */
@Controller
public class CommonAreaRestController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private TokenFactoryService tokenFactoryService;

    private static final Logger logger = LoggerFactory.getLogger(CommonAreaRestController.class);


    @RequestMapping(value = "rest/areas")
    @ResponseBody
    public AjaxResponse getArea(@RequestParam(value = "id", required = false) Integer id,
                                @RequestParam(value = "token", required = false) String token) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            logger.warn(" [req:{}]", token);
            if (tokenFactoryService.checkToken(token)) {
                if (id != null) {
                    List<Area> list = areaService.getBaseAreasByLocationId(id);
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, list);
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        }catch (Exception e){
            logger.error("error:{}",e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "rest/area/list")
    @ResponseBody
    public AjaxResponse getAreaList(@RequestParam(value = "id", required = false) Integer id,
                                @RequestParam(value = "token", required = false) String token) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            logger.warn(" [req:{}]", token);
            if (tokenFactoryService.checkToken(token)) {
                if (id != null) {
                    List<Area> list = areaService.getAreasByLocationId(id);
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, list);
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        }catch (Exception e){
            logger.error("error:{}",e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "rest/area/update")
    @ResponseBody
    public AjaxResponse areaUpdate(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "token", required = false) String token,
                                   @RequestParam(value = "name",required = false) String name,
                                   @RequestParam(value = "desc",required = false) String desc) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        try {
            logger.warn(" [req:{}]", token);
            if (tokenFactoryService.checkToken(token)) {
                if (id != null) {
                    Area area = areaService.get(id);
                    area.setName(name);
                    area.setDesc(desc);
                    areaService.update(area);
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, null);
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        }catch (Exception e){
            logger.error("error:{}",e);
        }
        return ajaxResponse;
    }

}
