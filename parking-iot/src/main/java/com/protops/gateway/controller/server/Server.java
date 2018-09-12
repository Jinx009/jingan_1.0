package com.protops.gateway.controller.server;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.enums.InterCode;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.service.BaseService;
import com.protops.gateway.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by jinx on 3/13/17.
 */
@Controller
@RequestMapping(value = "interface")
public class Server implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private ApplicationContext ctx;

    /**
     * 统一访问入口,注册,心跳,业务
     * @param device
     * @param operator
     * @param version
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "{device}/{operator}/{version}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public AjaxResponse executeInterface(
            @PathVariable(value = "device") String device,
            @PathVariable(value = "operator") String operator,
            @PathVariable(value = "version") String version,
            @RequestBody byte[] requestBody){
        InterCode interCode = InterCode.getByCode(device + "_" + operator + "_" + version);
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE,HttpConstant.ERROE_MSG,null);
        if(interCode==null){
            ajaxResponse = new AjaxResponse(HttpConstant.NOT_EXIST_CODE,HttpConstant.NOT_EXIST_MSG,null);
            ajaxResponse.setRespCode(HttpConstant.NOT_EXIST_CODE);
            ajaxResponse.setMsg(HttpConstant.NOT_EXIST_MSG);
        }else{
            try {
                String jsonData =  new String(requestBody, Constants.Default_SysEncoding);
                logger.warn("[executeInterface]data:{},bean:{}", jsonData, interCode.getServerBean());
                BaseService baseService = (BaseService)ctx.getBean(interCode.getServerBean());
                ajaxResponse = (AjaxResponse)baseService.execute(jsonData,interCode.getFunc());
            }catch (Exception e){
                e.printStackTrace();
                logger.warn(e.getMessage());
            }
        }
        return ajaxResponse;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
