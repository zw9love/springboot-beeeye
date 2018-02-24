package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.roncoo.education.util.MyUtil;
//@Controller
@RestController
@RequestMapping("/user")
public class BeeeyeUserController {
//    @PostMapping("get")
    @RequestMapping("get")
//    @ResponseBody
    public String get() throws JSONException {
//        HttpServletRequest request = getRequest();
//        HttpSession session = getSession();
//        String token = request.getHeader("token");
//        JSONObject role = (JSONObject) session.getAttribute(token);
//        Map<String, Object> json = MyUtil.getJsonData(request);
//        Map<String, Object> page = (Map<String, Object>) json.get("page");
//        String username = (String) role.get("username");
//        String where = " where username = '" + username + "'";
//        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
//        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
//        Page<UserDao> paginate = dao.paginate(pageNumber, pageSize, "select *", " from common_user" + where);
//        List<UserDao> list = paginate.getList();
//        JSONArray postList = new JSONArray();
//        for (UserDao user : list) {
//            String[] Names = user._getAttrNames();
//            JSONObject obj = new JSONObject();
//            for (String param : Names) {
//                Object object = user.get(param);
//                obj.put(param, object);
//            }
//            postList.put(obj);
//        }
//        int totalPage = paginate.getTotalPage();
//        int totalRow = paginate.getTotalRow();
//        JSONObject resObj = MyUtil.getPageJson(postList, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, "");
        return jsonObj.toString();
    }
}
