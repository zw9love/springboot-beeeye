package com.roncoo.education.controller;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Integrity;
import com.roncoo.education.mapper.IntegrityRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengwei
 * @date 2018/06/21 17:30
 */
@RestController
@RequestMapping("/BeeneedleIntegrity")
public class BeeeyeIntegrityController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeneedle_integrity";

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
//        Map<String, Object> row = new HashMap<String, Object>();
//        map.put("row", row);
        map.put("file_type", 1);
        map.put("full_path", "c:\2.txt");
        map.put("host_ids", "5b21f59c7b4dc909d34eaf12");
        map.put("name", "2.txt");
        map.put("type", 0);
        System.out.println(map.get("full_path"));
        System.out.println(MyUtil.getString(map, "full_path"));
//        JSONObject jsonObject = new BeeeyeIntegrityController().post(map);
    }

    /**
     * 获取完整性列表
     */
    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) throws JSONException {
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
        List<Integrity> list = jdbcTemplate.query(select + where + pageSql, params, new IntegrityRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count,countParams, Integer.class);
        int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }

    /**
     * 获取完整性列表(beeeye比较虎的做法)
     */
    @RequestMapping("get/{file_type}")
    public JSONObject get(@PathVariable String file_type, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String select = " SELECT * FROM  " + tableName;
        String where = " where host_ids = ? and file_type = ? and type = ?";
        String count = " SELECT count(*) FROM  " + tableName;
        String pageSql = " limit ?, ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = row.get("host_ids").toString();
        String type = row.get("type").toString();
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        int pageStart = (pageNumber - 1) * pageSize;
        Object[] params = new Object[]{hostIds, file_type, type, pageStart, pageSize};
        List<Integrity> list = jdbcTemplate.query(select + where + pageSql, params, new IntegrityRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count + where, new Object[]{hostIds, file_type, type}, Integer.class);
        int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }

    /**
     * 增加完整性
     * @return JSONObject
     */
    @RequestMapping("post")
    public JSONObject post(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getRandomString();
        String host_ids = MyUtil.getString(json, "host_ids");
        String name = MyUtil.getString(json, "name");
        String full_path = MyUtil.getString(json, "full_path");
        int file_type = MyUtil.getInt(json, "file_type");
        int type = MyUtil.getInt(json, "type");
        int time = MyUtil.getTime();
        String sql = " INSERT INTO " + tableName + " (ids, host_ids, name, full_path, file_type, type, time, hash) VALUES ( ?, ?, ?, ?, ?, ?, ?, '')";
        int effectRow = jdbcTemplate.update(sql, ids, host_ids, name, full_path, file_type, type, time);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("添加失败", 606, null);
        return jsonObj;
    }

    /**
     * 删除单个
     * @return JSONObject
     */
    @RequestMapping("/delete/{ids}")
    public JSONObject delete(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj;
        String sql = " delete from " + tableName + " where ids = ? ";
        int effectRow = jdbcTemplate.update(sql, ids);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("删除失败", 606, null);
        return jsonObj;
    }
}
