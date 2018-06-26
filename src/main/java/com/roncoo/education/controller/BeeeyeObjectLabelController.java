package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.ObjectLabel;
import com.roncoo.education.mapper.ObjectLabelRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/6/25
 */

@RestController
@RequestMapping("/BeeneedleObjectLabel")
public class BeeeyeObjectLabelController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeneedle_object_label";

    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request) {
        JSONObject jsonObj;
        String select = " SELECT * FROM  " + tableName;
        // SELECT * FROM beeneedle_object_label where type = 2 and ids in (select object_ids from beeneedle_object_host where host_ids = '5b23262076a7386c198d4577')
        String from = " as bol left join (select object_ids from beeneedle_object_host where host_ids = ?) as boh on bol.ids = boh.object_ids ";
        String where = " where bol.type = ? ";
        String count = " SELECT count(*) FROM  " + tableName + from;
        String pageSql = " limit ?, ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = row.get("host_ids").toString();
        Object[] params;
        if(page == null){
            String type = row.get("type").toString();
            params = new Object[]{hostIds, type};
            List<ObjectLabel> list = jdbcTemplate.query(select + from + where, params, new ObjectLabelRowMapper());
            jsonObj = MyUtil.getJson("成功", 200, list);
        }else{
            int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
            int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
            int pageStart = (pageNumber - 1) * pageSize;
            params = new Object[]{hostIds, pageStart, pageSize};
            Object[] countParams = {hostIds};
            List<ObjectLabel> list = jdbcTemplate.query(select + from + pageSql, params, new ObjectLabelRowMapper());
            // 获取总数
            Integer totalRow = jdbcTemplate.queryForObject(count, countParams, Integer.class);
            int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
            JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
            jsonObj = MyUtil.getJson("成功", 200, resObj);
        }

        return jsonObj;
    }

    @RequestMapping("get/{ids}")
    public JSONObject getById(@PathVariable String ids) {
        JSONObject jsonObj;
        if (ids != null) {
            String select = "select * from " + tableName + " where ids = ? ";
            ObjectLabel objectLabel = jdbcTemplate.queryForObject(select, new ObjectLabelRowMapper(), ids);
            jsonObj = MyUtil.getJson("成功", 200, objectLabel);
        } else {
            jsonObj = MyUtil.getJson("失败", 200, null);
        }
        return jsonObj;
    }

    @RequestMapping("put")
    public JSONObject put(HttpServletRequest request) {
        JSONObject jsonObj;
        String sql = " UPDATE " + tableName + " SET name = ?, path = ?, type = ?, sens_value = ?, reli_value = ? where ids = ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getString(json, "ids");
        String name = MyUtil.getString(json, "name");
        String path = MyUtil.getString(json, "path");
        int type = MyUtil.getInt(json, "type");
        int sens_value = MyUtil.getInt(json, "sens_value");
        int reli_value = MyUtil.getInt(json, "reli_value");
        Object[] params = new Object[]{name, path, type, sens_value, reli_value, ids};
        int effectRow = jdbcTemplate.update(sql, params);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败", 606, null);
        return jsonObj;
    }

    @Transactional
    @RequestMapping("post")
    public JSONObject post(HttpServletRequest request) {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getRandomString();
        String name = MyUtil.getString(json, "name");
        String path = MyUtil.getString(json, "path");
        String host_ids = MyUtil.getString(json, "host_ids");
        int type = MyUtil.getInt(json, "type");
        int sens_value = MyUtil.getInt(json, "sens_value");
        int reli_value = MyUtil.getInt(json, "reli_value");
        String sql = " INSERT INTO " + tableName + " (ids, name, path, type, sens_value, reli_value, serial, responsible) VALUES ( ?, ?, ?, ?, ?, ?, '', '')";
        String sqlHost = " INSERT INTO beeneedle_object_host (ids, object_ids, host_ids) VALUES (?, ?, ?) ";
        int effectRow = jdbcTemplate.update(sql, ids, name, path, type, sens_value, reli_value);
        int hostEffectRow = jdbcTemplate.update(sqlHost, MyUtil.getRandomString(), ids, host_ids);
        if (effectRow < 1 || hostEffectRow < 1)
            throw new RuntimeException();
        else
            jsonObj = MyUtil.getJson("成功", 200, null);
        return jsonObj;
    }

    @Transactional
    @RequestMapping("/delete/{ids}")
    public JSONObject deleteById(@PathVariable String ids) {
        JSONObject jsonObj;
        String sql = " delete from " + tableName + " where ids = ? ";
        String sqlHost = " delete from beeneedle_object_host where object_ids = ? ";
        int effectRow = jdbcTemplate.update(sql, ids);
        int hostEffectRow = jdbcTemplate.update(sqlHost, ids);
        if (effectRow < 1 || hostEffectRow < 1)
            throw new RuntimeException();
        else
            jsonObj = MyUtil.getJson("成功", 200, null);
        return jsonObj;
    }
}
