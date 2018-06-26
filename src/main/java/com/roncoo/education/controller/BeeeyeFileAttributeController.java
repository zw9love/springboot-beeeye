package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.FileAttribute;
import com.roncoo.education.bean.ObjectLabel;
import com.roncoo.education.bean.UserSubject;
import com.roncoo.education.mapper.FileAttributeRowMapper;
import com.roncoo.education.mapper.ObjectLabelRowMapper;
import com.roncoo.education.mapper.UserSubjectRowMapper;
import com.roncoo.education.util.MD5Util;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/6/26
 */

@RestController
@RequestMapping("/FileAttribute")
public class BeeeyeFileAttributeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeneedle_file_attribute";

    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request){
        String select = " SELECT * FROM  " + tableName;
        String where = " where host_ids = ? ";
        String count = " SELECT count(*) FROM  " + tableName + where;
        String pageSql = " limit ?, ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = row.get("host_ids").toString();
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        int pageStart = (pageNumber - 1) * pageSize;
        Object[] params = new Object[]{hostIds, pageStart, pageSize};
        Object[] countParams = new Object[]{hostIds};
        List<FileAttribute> list = jdbcTemplate.query(select + where + pageSql, params, new FileAttributeRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count, countParams, Integer.class);
        int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }

    @RequestMapping("post")
    public JSONObject post(HttpServletRequest request) {
        JSONObject jsonObj;
        String sql = "INSERT INTO " + tableName + " (ids, host_ids, file_path, privilege, user_name, create_time ) VALUES ( ?, ?, ?, ?, ?, ?)";
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getRandomString();
        String host_ids = MyUtil.getString(json, "host_ids");
        String file_path = MyUtil.getString(json, "file_path");
        String privilege = MyUtil.getString(json, "privilege");
        String user_name = MyUtil.getString(json, "user_name");
        int time = MyUtil.getTime();
        Object[] params = new Object[]{ids, host_ids, file_path, privilege, user_name, time};
        int effectRow = jdbcTemplate.update(sql, params);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败", 606, null);
        return jsonObj;
    }

    @RequestMapping("put")
    public JSONObject put(HttpServletRequest request) {
        JSONObject jsonObj;
        String sql = " UPDATE " + tableName + " SET privilege = ?, file_path = ? where ids = ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getString(json, "ids");
        String privilege = MyUtil.getString(json, "privilege");
        String file_path = MyUtil.getString(json, "file_path");
        Object[] params = new Object[]{privilege, file_path, ids};
        int effectRow = jdbcTemplate.update(sql, params);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败", 606, null);
        return jsonObj;
    }

    @RequestMapping("get/{ids}")
    public JSONObject getById(@PathVariable String ids) {
        JSONObject jsonObj;
        if (ids != null) {
            String select = "select * from " + tableName + " where ids = ? ";
            FileAttribute fileAttribute = jdbcTemplate.queryForObject(select, new FileAttributeRowMapper(), ids);
            jsonObj = MyUtil.getJson("成功", 200, fileAttribute);
        } else {
            jsonObj = MyUtil.getJson("失败", 200, null);
        }
        return jsonObj;
    }

    @RequestMapping("/delete/{ids}")
    public JSONObject deleteById(@PathVariable String ids) {
        JSONObject jsonObj;
        String sql = " delete from " + tableName + " where ids = ? ";
        int effectRow = jdbcTemplate.update(sql, ids);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败", 606, null);
        return jsonObj;
    }
}
