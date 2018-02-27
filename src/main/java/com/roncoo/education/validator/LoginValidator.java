package com.roncoo.education.validator;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.util.MyUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginValidator extends MainValidator {
    public JSONObject dologinValidate(Map<String, Object> json) throws IOException {
        JSONObject resMap = new JSONObject();
        String login_name = (String) json.get("login_name");
        String login_pwd = (String) json.get("login_pwd");
        Boolean nameFlag = validateRequired(login_name);
        Boolean pwdFlag = false;
        if (nameFlag) {
            pwdFlag = validateRequired(login_pwd);
            if (!pwdFlag)
//                response.getWriter().write(MyUtil.getJson("请输入密码", 606, null).toString());
                resMap.put("msg", "请输入密码");
        } else {
//            response.getWriter().write(MyUtil.getJson("请输入用户名", 606, null).toString());
            resMap.put("msg", "请输入用户名");
        }

        if(nameFlag && pwdFlag)
            resMap.put("status", 200);
        else
            resMap.put("status", 400);

        return resMap;
    }
}
