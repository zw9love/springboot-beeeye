package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Host;
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
 * @date 2018/02/26 17:19
 */

@RestController
@RequestMapping("/host")
public class BeeeyeHostController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeeye_host";
    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String select = " SELECT * FROM  " + tableName;
        String count = " SELECT count(*) FROM  " + tableName;
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        int pageStart = (pageNumber - 1) * pageSize;
        String pageSql = " limit ?, ? ";
        Object[] params = new Object[] { pageStart, pageSize };
        // 获取list
        List<Host> list=  (List<Host>) jdbcTemplate.query(select + pageSql, params, new RowMapper<Host>() {
            @Override
            public Host mapRow(ResultSet resultSet, int i) throws SQLException {
//                System.out.println("index = " + i);
                Host host = new Host();
                host.setHost_ids(resultSet.getString("host_ids"));
                host.setName(resultSet.getString("name"));
                host.setIp(resultSet.getString("ip"));
                host.setPort(resultSet.getInt("port"));
                host.setOs_type(resultSet.getString("os_type"));
                host.setOs_version(resultSet.getString("os_version"));
                host.setOs_arch(resultSet.getString("os_arch"));
                host.setLogin_name(resultSet.getString("login_name"));
                host.setLogin_pwd(resultSet.getString("login_pwd"));
                host.setStatus(resultSet.getInt("status"));
                return host;
            }
        });
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count, Integer.class);
        int totalPage = (int) Math.ceil((double)totalRow / (double)pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }

    @RequestMapping("/delete/{ids}")
    public JSONObject delete(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObj;
        if (ids != null) {
            String sql = " delete from beeeye_host where host_ids = ? ";
            int effectRow = jdbcTemplate.update(sql, ids);
            if (effectRow > 0)
                jsonObj = MyUtil.getJson("成功", 200, null);
            else
                jsonObj = MyUtil.getJson("失败，此ids不存在", 606, "");

        } else {
//            List<String> lists = MyUtil.getListData(request);
//            String sql = "delete from " + tableName + " where ids in (";
//            for (int i = 0; i < lists.size(); i++) {
//                sql += i == lists.size() - 1 ? "'" + lists.get(i) + "')": "'" + lists.get(i) + "', ";
//            }
//            // System.out.println(sql);
//            int effectCount = Db.delete(sql);
//            if (effectCount >= 0)
                jsonObj = MyUtil.getJson("成功", 200, "");
//            else
//                jsonObj = MyUtil.getJson("删除失败", 606, "");
        }

        return jsonObj;
    }
}
