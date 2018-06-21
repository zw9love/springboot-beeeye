package com.roncoo.education.controller;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Host;
import com.roncoo.education.bean.Integrity;
import com.roncoo.education.mapper.HostRowMapper;
import com.roncoo.education.mapper.IntegrityRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 获取完整性列表
     */
    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String select = " SELECT * FROM  " + tableName;
        String where = " where host_ids = ? ";
        String count = " SELECT count(*) FROM  " + tableName;
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String hostIds = row.get("host_ids").toString();
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        int pageStart = (pageNumber - 1) * pageSize;
        String pageSql = " limit ?, ? ";
        Object[] params = new Object[]{hostIds, pageStart, pageSize};
        List<Integrity> list = jdbcTemplate.query(select + where + pageSql, params, new IntegrityRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count, Integer.class);
        int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }
}
