package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zenngwei
 * @date 2018/02/26 16:29
 */
@RestController
@RequestMapping("/role")
public class BeeeyeRoleController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("getCur")
    public JSONObject get(HttpServletRequest request, HttpServletResponse response){
        // System.out.println("进了getCur方法");
        HttpSession session = request.getSession();
        String token = request.getHeader("token");
        JSONObject jsonObj;
        if(token.equals("debug")){
            JSONObject resObj = new JSONObject();
            resObj.put("zh_names", "超级管理员");
            resObj.put("login_name", "root");
            resObj.put("ids", 0);
            jsonObj = MyUtil.getJson("成功", 200, resObj);
            return jsonObj;
        }

        JSONObject role = (JSONObject) session.getAttribute(token);
        if (role == null) {
            jsonObj = MyUtil.getJson("失败", 606, "");
        } else {
            String username = (String) role.get("username");
            String login_name = (String) role.get("login_name");
            String ids = (String) role.get("ids");
            JSONObject resObj = new JSONObject();
            resObj.put("zh_names", username);
            resObj.put("login_name", login_name);
            resObj.put("ids", ids);
            jsonObj = MyUtil.getJson("成功", 200, resObj);
        }
        return jsonObj;
    }
}
