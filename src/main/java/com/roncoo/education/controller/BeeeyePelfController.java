package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Pelf;
import com.roncoo.education.mapper.PelfRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/06/23 17:19
 */

@RestController
@RequestMapping("/BeeneedlePelf")
public class BeeeyePelfController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeeye_white";

    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request){
        JSONObject jsonObj = null;
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = row.get("host_ids").toString();
        String pelfstatus = (String) row.get("pelfstatus");

        Map<String, Object> size = (Map<String, Object>) json.get("size");
        int pageSize = (int) Double.parseDouble(size.get("size").toString());
        String beforeId = size.get("beforeId").toString().trim();
        int pageStart, pageNumber;
        if (beforeId.equals("")) {
            pageStart = 0;
            pageNumber = 1;
        } else {
            pageStart = (int) Double.parseDouble(beforeId);
            pageNumber = pageStart / pageSize + 1;
        }
        Map<String, Object> query = (Map<String, Object>) json.get("query");
        List<Map<String, Object>> sort = (List<Map<String, Object>>) json.get("sort");

        String querySql = " ", sortSql = " ";

//                    System.out.println("query = " + query);
//                    System.out.println("sort = " + sort);
        if(query != null){
            for(String key : query.keySet()){
                Object value = query.get(key);
                querySql += " and " + key + " LIKE '%" + value + "%' ";
            }
        }

        if(sort != null){
            Map<String, Object> map = sort.get(0);
            String col = (String)map.get("col");
            String order = (String)map.get("order");
            sortSql = " order by " + col + " " + order;
        }

        String select = "select * from " + tableName;
        String count = "select count(*) from " + tableName;
        String where = " where pelfstatus = ? and host_ids = ? " + querySql + sortSql;
        String limit = " limit ?, ? ";
        String sql = select + where + limit;
        Object[] sqlParams = new Object[]{pelfstatus, hostIds, pageStart, pageSize};
        Object[] countParams = new Object[]{pelfstatus, hostIds};
        List<Pelf> list = jdbcTemplate.query(sql, sqlParams, new PelfRowMapper());
        Integer totalRow = jdbcTemplate.queryForObject(count + where, countParams, Integer.class);

        JSONObject sizeObj = new JSONObject();
        sizeObj.put("size", pageSize);
        sizeObj.put("offset", pageSize);
        sizeObj.put("beforeId", pageSize + pageStart);
        JSONObject resObj = MyUtil.getSizeJson(list, sizeObj, totalRow);
        jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }

    @RequestMapping("put")
    public JSONObject put(HttpServletRequest request) {
        JSONObject jsonObj;
        String sql = " UPDATE " + tableName + " SET pelfstatus = ? where ids = ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        String pelfstatus = MyUtil.getString(json, "pelfstatus");
        String ids = MyUtil.getString(json, "ids");
        Object[] params = new Object[]{pelfstatus, ids};
        int effectRow = jdbcTemplate.update(sql, params);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败", 606, null);
        return jsonObj;
    }

    /**
     * 删除单个
     * @return JSONObject
     */
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
