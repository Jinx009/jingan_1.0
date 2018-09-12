package com.protops.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by damen on 2016/2/14.
 */
@Controller
@RequestMapping(value = "weixin/h5")
public class GlobalErrController {

    @RequestMapping(value = {"/error"})
    public String errs(HttpServletRequest request, Model model) {


        return "error";
    }
}
