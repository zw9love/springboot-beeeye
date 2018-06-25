package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.ProcessSubject;
import com.roncoo.education.mapper.ProcessSubjectRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/6/25
 */

@RestController
@RequestMapping("/BeeneedleProcessSubject")
public class BeeeyeProcessSubjectController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "beeneedle_process_subject";

    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request) {
        // select * from beeneedle_process_subject as bps left join (select process_ids from beeneedle_process_host where host_ids = ?) as bph on bps.ids = bph.process_ids
        String select = " SELECT * FROM  " + tableName;
//        String where = " where ids in (select process_ids from beeneedle_process_host where host_ids = ? )";
        String where = " as bps left join (select process_ids from beeneedle_process_host where host_ids = ?) as bph on bps.ids = bph.process_ids ";
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
        List<ProcessSubject> list = jdbcTemplate.query(select + where + pageSql, params, new ProcessSubjectRowMapper());
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
            ProcessSubject processSubject = jdbcTemplate.queryForObject(select, new ProcessSubjectRowMapper(), ids);
            jsonObj = MyUtil.getJson("成功", 200, processSubject);
        } else {
            jsonObj = MyUtil.getJson("失败", 200, null);
        }
        return jsonObj;
    }

    @RequestMapping("put")
    public JSONObject put(HttpServletRequest request) {
        JSONObject jsonObj;
        String sql = " UPDATE " + tableName + " SET name = ?, path = ?, sens_value = ?, reli_value = ? where ids = ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getString(json, "ids");
        String name = MyUtil.getString(json, "name");
        String path = MyUtil.getString(json, "path");
        int sens_value = MyUtil.getInt(json, "sens_value");
        int reli_value = MyUtil.getInt(json, "reli_value");
        Object[] params = new Object[]{name, path, sens_value, reli_value, ids};
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
        int sens_value = MyUtil.getInt(json, "sens_value");
        int reli_value = MyUtil.getInt(json, "reli_value");
        String sql = " INSERT INTO " + tableName + " (ids, name, path, sens_value, reli_value, hash) VALUES ( ?, ?, ?, ?, ?, '')";
        String sqlHost = " INSERT INTO beeneedle_process_host (ids, process_ids, host_ids) VALUES (?, ?, ?) ";
        int effectRow = jdbcTemplate.update(sql, ids, name, path, sens_value, reli_value);
//        int a = 1 / 0;
        int hostEffectRow = jdbcTemplate.update(sqlHost, MyUtil.getRandomString(), ids, host_ids);
        if (effectRow < 1 || hostEffectRow < 1)
            //当sql语句的任何一条没有执行成功，就回滚的初始状态
            throw new RuntimeException(); // 加上了@Transactional注解，表示使用事务，当有异常抛出时，就会自动回滚。
         else
            jsonObj = MyUtil.getJson("成功", 200, null);

//        try {
//            int effectRow = jdbcTemplate.update(sql, ids, name, path, sens_value, reli_value);
////            int a = 1 / 0;
//            int hostEffectRow = jdbcTemplate.update(sqlHost, MyUtil.getRandomString(), ids, host_ids);
//            conn = jdbcTemplate.getDataSource().getConnection();
//            conn.setAutoCommit(false);
//            if (effectRow < 1 || hostEffectRow < 1) {
//                //当sql语句的任何一条没有执行成功，就回滚的初始状态
//                conn.rollback();
//            } else {
//                flag = true;
//                conn.commit();
//            }
//        } catch (Exception e) {
//            try {
//                System.out.println("出现异常，回滚！！！");
//                conn = jdbcTemplate.getDataSource().getConnection();
//                conn.setAutoCommit(false);
//                conn.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                conn = jdbcTemplate.getDataSource().getConnection();
//                conn.setAutoCommit(false);
//                if (flag) {
//                    conn.commit();
//                } else {
//                    conn.rollback();
//                }
//                //把事务提交，结束，最后把conn返回给连接池
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

        return jsonObj;
    }

    @Transactional
    @RequestMapping("/delete/{ids}")
    public JSONObject delete(@PathVariable String ids) {
        JSONObject jsonObj;
        String sql = " delete from " + tableName + " where ids = ? ";
        String sqlHost = " delete from beeneedle_process_host where process_ids = ? ";
        int effectRow = jdbcTemplate.update(sql, ids);
        int hostEffectRow = jdbcTemplate.update(sqlHost, ids);
        if (effectRow < 1 || hostEffectRow < 1) {
            //当sql语句的任何一条没有执行成功，就回滚的初始状态
            throw new RuntimeException(); // 加上了@Transactional注解，表示使用事务，当有异常抛出时，就会自动回滚。
        } else
            jsonObj = MyUtil.getJson("成功", 200, null);
        return jsonObj;
//        Connection conn = null;
//        Boolean flag = false;
//        try {
//            DataSource dataSource = jdbcTemplate.getDataSource();
//            conn = dataSource.getConnection();
//            conn.setAutoCommit(false);
//            if (effectRow < 1 || hostEffectRow < 1) {
//                //当sql语句的任何一条没有执行成功，就回滚的初始状态
//                conn.rollback();
//            }
//        } catch (Exception e) {
//            try {
//                conn.setAutoCommit(false);
//                conn.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        } finally {
//            try {
//                flag = true;
//                conn.commit();
//                //把事务提交，结束，最后把conn返回给连接池
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        if (flag)
//            jsonObj = MyUtil.getJson("成功", 200, null);
//        else
//            jsonObj = MyUtil.getJson("失败", 606, null);
//        return jsonObj;
    }
}
