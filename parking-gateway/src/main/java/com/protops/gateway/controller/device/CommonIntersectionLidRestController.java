package com.protops.gateway.controller.device;

import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.common.CommonVo;
import com.protops.gateway.service.common.CommonVoService;
import com.protops.gateway.service.common.TokenFactoryService;
import com.protops.gateway.util.StringUtils;
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
public class CommonIntersectionLidRestController {

    private static final Logger logger = LoggerFactory.getLogger(CommonIntersectionLidRestController.class);

    @Autowired
    private TokenFactoryService tokenFactoryService;
    @Autowired
    private CommonVoService commonVoService;


    @RequestMapping(value = "rest/intersection/lids")
    @ResponseBody
    public AjaxResponse lids(@RequestParam(value = "token", required = false) String token,
                             @RequestParam(value = "area", required = false) Integer area) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("CommonIntersectionLidRestController.lids [req:{},{}]", token, area);
        if (tokenFactoryService.checkToken(token)) {
            if (StringUtils.isNotBlank(token) && area != null) {
                List<CommonVo> list = commonVoService.findIntersectionLidByArea(area);
                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, list);
            }
        } else {
            ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
        }
        return ajaxResponse;
    }

}
