/**
 * 2015-2016 龙果学院 (www.roncoo.com)
 */
package com.roncoo.education.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.roncoo.education.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zengwei
 */
@Controller
//@RestController
//@RequestMapping(value = "/")
public class IndexController {
    //	@RequestMapping
//	@RequestMapping(value = "/index")//	@GetMapping("/index")
//    @GetMapping("/**")
    private static boolean loginActive = false;
    private static final Logger logger =  LoggerFactory.getLogger(IndexController.class);

    public static void setLoginActive(boolean loginActive) {
        IndexController.loginActive = loginActive;
    }

    @GetMapping("/*")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return "error";
    }

    @RequestMapping("/")
    public String jumpIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (loginActive) {
            setLoginActive(false);
            return "index";
        } else {
            response.sendRedirect("/login"); // 登陆成功，return "index" 失败，重定向到登录页
            return null;
        }
    }

    // @RequestParam 简单类型的绑定，可以出来get和post
    // @RequestMapping(value = "/login",method=RequestMethod.GET) 还可规定请求方式
//    @RequestMapping(value = "/get")
//    public HashMap<String, Object> get(@RequestParam String name) {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("title", "hello world");
//        map.put("name", name);
////		System.out.println(map.toString());
//        return map;
//    }

    // @PathVariable 获得请求url中的动态参数
//    @RequestMapping(value = "/get/{id}/{name}")
//    public UserDao getUser(@PathVariable int id, @PathVariable String name) {
//        UserDao user = new UserDao();
//        user.setId(id);
//        user.setName(name);
//        user.setDate(new Date());
//        return user;
//    }

}
