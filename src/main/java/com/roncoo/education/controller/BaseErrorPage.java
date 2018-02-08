package com.roncoo.education.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 2018/2/8.
 */
@Controller
@RequestMapping("/error")
public class BaseErrorPage implements ErrorController {
    @Override
    public String getErrorPath() {
        System.out.println("进来了getErrorPath方法");
        return "error";
    }

    @RequestMapping
    public String error() {
        System.out.println("进来了error方法");
        return getErrorPath();
    }

}
