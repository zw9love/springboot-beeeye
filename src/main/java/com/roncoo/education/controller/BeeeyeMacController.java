package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Mac;
import com.roncoo.education.mapper.MacRowMapper;
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
//        String select = " SELECT * FROM  " + tableName;
//        String where = " where host_ids = ? and type = ? ";
        String select = "select bm.ids, bm.host_ids, bm.sub_ids, bm.obj_ids, bm.privilege, bm.type, bps.name as subject, bol.name as object ";
        String from = "from beeneedle_mac as bm " +
                "left join beeneedle_process_subject bps " +
                "on bps.ids=bm.sub_ids " +
                "join beeneedle_object_label bol " +
                "on bol.ids=bm.obj_ids ";
        String where = " where bm.host_ids = ? and bm.type = ? and bm.obj_ids in ( select ids from beeneedle_object_label where type = ? ) ";
        String count = " SELECT count(*) FROM  " + tableName;
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
        List<Mac> list = jdbcTemplate.query(select + from + where + pageSql, params, new MacRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count, Integer.class);
        int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }
}
