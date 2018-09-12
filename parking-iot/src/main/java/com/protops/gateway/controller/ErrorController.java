package com.protops.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhouzhihao on 2014/11/4.
 */
@Controller
public class ErrorController {

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String page404() throws Exception {

        return "404";

    }
}
