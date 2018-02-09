package com.roncoo.education.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author zengwei
 */
//@RestController
//@RequestMapping(value = "/login")
@Controller
public class LoginController {
    private static final Logger logger =  LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("login")
    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        logger.info("这里是LoginController的index方法");
        logger.info("=======================");
        logger.info("=========Login=========");
        logger.info("=======================");
//        request.getRequestDispatcher("login.html").forward(request, response);
        return "login";
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("status", 200);
//        map.put("msg", "成功");
//        map.put("data", null);
//        return map;
    }


    @RequestMapping(value = "/login/dologin")
    public HashMap<String, Object> doLogin(){
        System.out.println("进来doLogin方法了");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "成功");
        map.put("data", null);
        return map;
    }
}
