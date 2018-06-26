package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.UserSubject;
import com.roncoo.education.mapper.UserSubjectRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/6/25
 */

@RestController
@RequestMapping("/BeeneedleUserSubject")
public class BeeeyeUserSubjectController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeneedle_user_subject";

    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request){
        String select = " SELECT * FROM  " + tableName;
        String where = " as bus left join (select user_ids from beeneedle_user_host where host_ids = ?) as buh on bus.ids = buh.user_ids ";
        String count = " SELECT count(*) FROM  " + tableName;
        String pageSql = " limit ?, ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = row.get("host_ids").toString();
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        int pageStart = (pageNumber - 1) * pageSize;
        Object[] params = new Object[]{hostIds, pageStart, pageSize};
        List<UserSubject> list = jdbcTemplate.query(select + where + pageSql, params, new UserSubjectRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count, Integer.class);
        int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }

    @RequestMapping("get/{ids}")
    public JSONObject getById(@PathVariable String ids) {
        JSONObject jsonObj;
        if (ids != null) {
            String select = "select * from " + tableName + " where ids = ? ";
            UserSubject userSubject = jdbcTemplate.queryForObject(select, new UserSubjectRowMapper(), ids);
            jsonObj = MyUtil.getJson("成功", 200, userSubject);
        } else {
            jsonObj = MyUtil.getJson("失败", 200, null);
        }
        return jsonObj;
    }

    @RequestMapping("put")
    public JSONObject put(HttpServletRequest request) {
        JSONObject jsonObj;
        String sql = " UPDATE " + tableName + " SET username = ?, dept = ?, mail = ?, sens_value = ?, reli_value = ? where ids = ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getString(json, "ids");
        String username = MyUtil.getString(json, "username");
        String dept = MyUtil.getString(json, "dept");
        String mail = MyUtil.getString(json, "mail");
        int sens_value = MyUtil.getInt(json, "sens_value");
        int reli_value = MyUtil.getInt(json, "reli_value");
        Object[] params = new Object[]{username, dept, mail, sens_value, reli_value, ids};
        int effectRow = jdbcTemplate.update(sql, params);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败", 606, null);
        return jsonObj;
    }


//    @RequestMapping("post")
    @RequestMapping("sysusepost")
    public JSONObject post(HttpServletRequest request) {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getRandomString();
        String username = MyUtil.getString(json, "username");
        String dept = MyUtil.getString(json, "dept");
        String mail = MyUtil.getString(json, "mail");
        String host_ids = MyUtil.getString(json, "host_ids");
        int sens_value = MyUtil.getInt(json, "sens_value");
        int reli_value = MyUtil.getInt(json, "reli_value");
        String sql = " INSERT INTO " + tableName + " (ids, username, dept, mail, sens_value, reli_value, login_name, contact, cert_sn) VALUES ( ?, ?, ?, ?, ?, ?, '', '', '')";
        String sqlHost = " INSERT INTO beeneedle_user_host (ids, user_ids, host_ids) VALUES (?, ?, ?) ";
        int effectRow = jdbcTemplate.update(sql, ids, username, dept, mail, sens_value, reli_value);
        int hostEffectRow = jdbcTemplate.update(sqlHost, MyUtil.getRandomString(), ids, host_ids);
        if (effectRow < 1 || hostEffectRow < 1)
            //当sql语句的任何一条没有执行成功，就回滚的初始状态
            throw new RuntimeException(); // 加上了@Transactional注解，表示使用事务，当有异常抛出时，就会自动回滚。
        else
            jsonObj = MyUtil.getJson("成功", 200, null);
        return jsonObj;
    }

    @RequestMapping("/delete/{ids}")
    public JSONObject deleteById(@PathVariable String ids) {
        JSONObject jsonObj;
        String sql = " delete from " + tableName + " where ids = ? ";
        String sqlHost = " delete from beeneedle_user_host where user_ids = ? ";
        int effectRow = jdbcTemplate.update(sql, ids);
        int hostEffectRow = jdbcTemplate.update(sqlHost, ids);
        if (effectRow < 1 || hostEffectRow < 1) {
            //当sql语句的任何一条没有执行成功，就回滚的初始状态
            throw new RuntimeException(); // 加上了@Transactional注解，表示使用事务，当有异常抛出时，就会自动回滚。
        } else
            jsonObj = MyUtil.getJson("成功", 200, null);
        return jsonObj;
    }
}
