package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.roncoo.education.util.MyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zengwei
 */
//@RestController
//@RequestMapping(value = "/login")
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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


//    @RequestMapping(value = "/login/dologin")
//    public HashMap<String, Object> doLogin(){
//        System.out.println("进来doLogin方法了");
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("status", 200);
//        map.put("msg", "成功");
//        map.put("data", null);
//        return map;
//    }

    @RequestMapping(value = "/login/dologin")
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进来doLogin方法了");
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
//        JSONObject jsonObj = new JSONObject();
//        JSONArray list = new JSONArray();
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("status", 606);
//        map.put("msg", "失败");
//        map.put("data", "测试一下");
        JSONObject jsonObj = MyUtil.getJson("失败了哦", 606, null);
        response.getWriter().write(jsonObj.toString());
    }

}
