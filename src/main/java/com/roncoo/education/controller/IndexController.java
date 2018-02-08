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
@RequestMapping(value = "/")
public class IndexController {
    //	@RequestMapping
//	@RequestMapping(value = "/index")//	@GetMapping("/index")
    @GetMapping("/*")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getSession().getServletContext().getRealPath("/");
        System.out.println("====================================");
        System.out.println("contextPath = " + contextPath);
        System.out.println("====================================");
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String path = request.getRequestURI();
        if(path.equals("/"))
            response.getWriter().write("首页");
        else
            response.sendRedirect("/");
//		return "index方法。Hello SpringBoot!!!";
//        return "index";
    }

    // @RequestParam 简单类型的绑定，可以出来get和post
    // @RequestMapping(value = "/login",method=RequestMethod.GET) 还可规定请求方式
    @RequestMapping(value = "/get")
    public HashMap<String, Object> get(@RequestParam String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello world");
        map.put("name", name);
//		System.out.println(map.toString());
        return map;
    }

    // @PathVariable 获得请求url中的动态参数
    @RequestMapping(value = "/get/{id}/{name}")
    public User getUser(@PathVariable int id, @PathVariable String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setDate(new Date());
        return user;
    }

}
