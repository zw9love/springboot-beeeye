package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.roncoo.education.util.MyUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@Controller
@RestController
@RequestMapping("/user")
public class BeeeyeUserController {
//    @PostMapping("get")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("get")
    public JSONObject get() throws JSONException {
        String select = " SELECT * FROM common_user ";
        List<User> list=  (List<User>) jdbcTemplate.query(select, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
//                System.out.println("index = " + i);
                User user = new User();
                user.setIds(resultSet.getString("ids"));
                user.setLoginName(resultSet.getString("login_name"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setStatus(resultSet.getString("status"));
                user.setRoleIds(resultSet.getString("role_ids"));
                return user;
            }
        });
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
        JSONObject jsonObj = MyUtil.getJson("成功", 200, list);
        return jsonObj;
    }
}
