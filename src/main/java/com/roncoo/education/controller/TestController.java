/**
 * 2015-2016 龙果学院 (www.roncoo.com)
 */
package com.roncoo.education.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.springframework.web.bind.annotation.*;

import com.roncoo.education.bean.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zengwei
 */
@RestController
@RequestMapping(value = "/login")
public class TestController {
    // @RequestParam 简单类型的绑定，可以出来get和post
    // @RequestMapping(value = "/login",method=RequestMethod.GET) 还可规定请求方式
    @RequestMapping(value = "/dologin")
    public HashMap<String, Object> get(@RequestParam String login_name,@RequestParam String login_pwd) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("login_name", login_name);
        map.put("login_pwd", login_pwd);
        return map;
    }

}
