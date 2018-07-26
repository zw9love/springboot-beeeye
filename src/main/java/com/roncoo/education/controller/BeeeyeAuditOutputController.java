package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Pelf;
import com.roncoo.education.mapper.PelfRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/7/26
 */

@RestController
@RequestMapping("/audit/output/")
public class BeeeyeAuditOutputController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "fileinput_detail";

    @Transactional
    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request){
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
//        Map<String, Object> row = (Map<String, Object>) json.get("row");
//        String hostIds = row.get("host_ids").toString();
//        String pelfstatus = (String) row.get("pelfstatus");
        String userIds = MyUtil.getString(json, "userIds");

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

        String querySql = "", sortSql = "";
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
        String where = " where user_ids = ? " + querySql + sortSql;
        String limit = " limit ?, ? ";
        String sql = select + where + limit;
        Object[] sqlParams = new Object[]{userIds, pageStart, pageSize};
        Object[] countParams = new Object[]{userIds};
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

    @RequestMapping("importFile")
    private void importFile(String user_ids, String default_path, String filename, int create_time, String type) {
        String sql = "INSERT INTO " + tableName + " (user_ids, default_path, filename, create_time, type) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, user_ids, default_path, filename, create_time, type);
    }

    @RequestMapping("post")
    public JSONObject post(HttpServletRequest request){
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
        int startTime = MyUtil.getInt(json, "start");
        int endTime = MyUtil.getInt(json, "end");
        String userIds = MyUtil.getString(json, "userIds");
        List<String> typeList = MyUtil.getStringList(json, "type");
        List<Object> dataList = new ArrayList<Object>();
        int nowTime = MyUtil.getTime();
//        String path = "C:\\\\jfinal-beeeye\\\\upload\\\\";
        String path = "Users\\\\zengwei\\\\work\\\\springboot-beeeye\\\\upload\\\\";
        String sql = "INSERT INTO " + tableName + " (user_ids, default_path, filename, create_time, type) VALUES ";
        String where = " where time >= " + startTime + " and time <= " + endTime;
        int index = 0;
        int len = typeList.size();
        for (String item : typeList) {
            String tempName = "";
            String outputSql = "";
            String tempIds = MyUtil.getRandomString() + ".csv";
            String seatSql = index == len - 1 ? " (?,?,?,?,?) " : " (?,?,?,?,?), ";
            dataList.add(userIds);
            dataList.add(path);
            dataList.add(item + tempIds);
            dataList.add(nowTime);
            dataList.add(item);
            sql += seatSql;
            switch (item) {
                case "BeeneedlePolicyLoadAudit":
                    tempName = "beeneedle_policy_load_audit";
                    break;
                case "aud_login":
                    tempName = "common_login_audit";
                    break;
                case "BeeneedleProcessAccessAudit":
                    tempName = "beeneedle_process_access_audit";
                    break;
                case "BeeneedleConfigAudit":
                    tempName = "beeneedle_config_audit";
                    break;
                case "BeeneedleGrayFileAudit":
                    tempName = "beeneedle_gray_file_audit";
                    break;
            }
            // show variables like '%secure%';查看 secure-file-priv 当前的值是什么
            // 导出的数据必须是这个值的指定路径才可以导出，默认有可能是NULL就代表禁止导出
            outputSql = "select * from " + tempName + where + " into outfile '" + (path + item + tempIds) + "' fields terminated by " + " ',' " + " optionally enclosed by '' lines terminated by '\\r\\n';";
//            System.out.println(outputSql);
            index++;
            importFile(userIds, path, item + tempIds, nowTime, item);
            jdbcTemplate.update(outputSql);
        }
        jsonObj = MyUtil.getJson("成功", 200, "");
        return jsonObj;
    }
}
