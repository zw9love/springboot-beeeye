package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Mac;
import com.roncoo.education.mapper.MacRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/6/25
 */

@RestController
@RequestMapping("/BeeneedleMac")
public class BeeeyeMacController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeneedle_mac";

    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request) {
        String select = "select bm.ids, bm.host_ids, bm.sub_ids, bm.obj_ids, bm.privilege, bm.type, bps.name as subject, bol.name as object ";
        String from = "from beeneedle_mac as bm " +
                "left join beeneedle_process_subject bps " +
                "on bps.ids=bm.sub_ids " +
                "join beeneedle_object_label bol " +
                "on bol.ids=bm.obj_ids ";
        String where = " where bm.host_ids = ? and bm.type = ? and bm.obj_ids in ( select ids from beeneedle_object_label where type = ? ) ";
        String count = " SELECT count(*) " + from + where;
        String pageSql = " limit ?, ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = row.get("host_ids").toString();
        String type = row.get("type").toString();
        String labelType = row.get("labelType").toString();
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        int pageStart = (pageNumber - 1) * pageSize;
        Object[] params = new Object[]{hostIds, type, labelType, pageStart, pageSize};
        Object[] countParams = {hostIds, type, labelType};
        List<Mac> list = jdbcTemplate.query(select + from + where + pageSql, params, new MacRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count, countParams, Integer.class);
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
            Mac mac = jdbcTemplate.queryForObject(select, new RowMapper<Mac>() {
                @Override
                public Mac mapRow(ResultSet resultSet, int i) throws SQLException {
                    Mac mac = new Mac();
                    mac.setIds(resultSet.getString("ids"));
                    mac.setHost_ids(resultSet.getString("host_ids"));
                    mac.setSub_ids(resultSet.getString("sub_ids"));
                    mac.setObj_ids(resultSet.getString("obj_ids"));
                    mac.setPrivilege(resultSet.getInt("privilege"));
                    mac.setType(resultSet.getInt("type"));
                    return mac;
                }
            }, ids);
            jsonObj = MyUtil.getJson("成功", 200, mac);
        } else {
            jsonObj = MyUtil.getJson("失败", 200, null);
        }
        return jsonObj;
    }

    @RequestMapping("post")
    public JSONObject post(HttpServletRequest request) {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getRandomString();
        String obj_ids = MyUtil.getString(json, "obj_ids");
        String sub_ids = MyUtil.getString(json, "sub_ids");
        String host_ids = MyUtil.getString(json, "host_ids");
        int privilege = MyUtil.getInt(json, "privilege");
        int type = MyUtil.getInt(json, "type");
        String sql = " INSERT INTO " + tableName + " (ids, host_ids, sub_ids, obj_ids, privilege, type) VALUES ( ?, ?, ?, ?, ?, ?)";
        Object[] params = {ids, host_ids, sub_ids, obj_ids, privilege, type};
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
        String sql = " UPDATE " + tableName + " SET sub_ids = ?, obj_ids = ?, privilege = ? where ids = ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getString(json, "ids");
        String sub_ids = MyUtil.getString(json, "sub_ids");
        String obj_ids = MyUtil.getString(json, "obj_ids");
        int privilege = MyUtil.getInt(json, "privilege");
        Object[] params = new Object[]{sub_ids, obj_ids, privilege, ids};
        int effectRow = jdbcTemplate.update(sql, params);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败", 606, null);
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
