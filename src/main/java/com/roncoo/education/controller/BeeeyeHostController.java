package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Host;
import com.roncoo.education.mapper.HostRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    /**
     * Host对象私有方法
     * @param versionList
     * @param version
     * @param arch
     */

    private void getList(List<JSONObject> versionList, String version, String arch){
        JSONObject versionMap = new JSONObject();
        List<String> archList = new ArrayList<String>();
        archList.add(arch);
        versionMap.put("value", version);
        versionMap.put("arch", archList);
        versionList.add(versionMap);
    }

    /**
     * 获取主机列表
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String select = " SELECT * FROM  " + tableName;
        Map<String, Object> json = MyUtil.getJsonData(request);
        if(json != null){
            Map<String, Object> page = (Map<String, Object>) json.get("page");
            if(page != null){
                String count = " SELECT count(*) FROM  " + tableName;
                int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
                int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
                int pageStart = (pageNumber - 1) * pageSize;
                String pageSql = " limit ?, ? ";
                Object[] params = new Object[]{pageStart, pageSize};
                List<Host> list = jdbcTemplate.query(select + pageSql, params, new HostRowMapper());
                // 获取总数
                Integer totalRow = jdbcTemplate.queryForObject(count, Integer.class);
                int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
                JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
                return MyUtil.getJson("成功", 200, resObj);
            }
        }

        List<Host> list = jdbcTemplate.query(select, new HostRowMapper());
        JSONObject jsonObj = MyUtil.getJson("成功", 200, list);

        // 获取list
//        List<Host> list = (List<Host>) jdbcTemplate.query(select + pageSql, params, new RowMapper<Host>() {
//            @Override
//            public Host mapRow(ResultSet resultSet, int i) throws SQLException {
//                Host host = new Host();
//                host.setHost_ids(resultSet.getString("host_ids"));
//                host.setName(resultSet.getString("name"));
//                host.setIp(resultSet.getString("ip"));
//                host.setPort(resultSet.getInt("port"));
//                host.setOs_type(resultSet.getString("os_type"));
//                host.setOs_version(resultSet.getString("os_version"));
//                host.setOs_arch(resultSet.getString("os_arch"));
//                host.setLogin_name(resultSet.getString("login_name"));
//                host.setLogin_pwd(resultSet.getString("login_pwd"));
//                host.setStatus(resultSet.getInt("status"));
//                return host;
//            }
//        });


        return jsonObj;
    }

    /**
     * 获取单个主机信息
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("/get/{ids}")
    public JSONObject getById(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj;
        if (ids != null) {
            String sql = " SELECT * FROM beeeye_host where host_ids = ? ";
            Host host = jdbcTemplate.queryForObject(sql, new HostRowMapper(), ids);
            jsonObj = MyUtil.getJson("成功", 200, host);
//            Object[] params = new Object[]{ids};
//            List<Host> list = jdbcTemplate.query(sql, params, new RowMapper<Host>() {
//                @Override
//                public Host mapRow(ResultSet resultSet, int i) throws SQLException {
//                    Host host = new Host();
//                    host.setHost_ids(resultSet.getString("host_ids"));
//                    host.setName(resultSet.getString("name"));
//                    host.setLogin_name(resultSet.getString("login_name"));
//                    host.setLogin_pwd(resultSet.getString("login_pwd"));
//                    host.setIp(resultSet.getString("ip"));
//                    host.setPort(resultSet.getInt("port"));
//                    host.setOs_type(resultSet.getString("os_type"));
//                    host.setOs_version(resultSet.getString("os_version"));
//                    host.setOs_arch(resultSet.getString("os_arch"));
//                    host.setStatus(resultSet.getInt("status"));
//                    return host;
//                }
//            });
//            jsonObj = MyUtil.getJson("成功", 200, list.get(0));
        } else {
            jsonObj = MyUtil.getJson("失败", 200, null);
        }
        return jsonObj;
    }

    /**
     * 检查主机是否能连接
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("checkInfo")
    public JSONObject checkInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj = MyUtil.getJson("成功", 200, null);
        return jsonObj;
    }

    /**
     * 获取系统列表
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("getSystems")
    public JSONObject getSystems(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj;
        List<JSONObject> linuxVersionList = new ArrayList<JSONObject>();
        List<JSONObject> WindowsVersionList = new ArrayList<JSONObject>();
        List<JSONObject> resList = new ArrayList<JSONObject>();
        JSONObject linuxMap = new JSONObject();
        JSONObject windowsMap = new JSONObject();
        linuxMap.put("name", "linux");
        linuxMap.put("version", linuxVersionList);
        windowsMap.put("name", "windows");
        windowsMap.put("version", WindowsVersionList);
        resList.add(linuxMap);
        resList.add(windowsMap);
        String path = "C:/systems";
        File file = new File(path);
        if (file.exists()) {
            String[] list = file.list();
            for (String filename : list) {
                String[] splitList = filename.split("-");
                String type = splitList[1];
                String version = splitList[2];
                String arch = splitList[3];
                for (JSONObject resItem : resList) {
                    String versionName = resItem.getString("name");
                    List<JSONObject> versionList = (List<JSONObject>) resItem.get("version");
                    if (versionName.equals(type)) {
                        if (versionList.size() == 0) {
                            getList(versionList, version, arch);
//                            JSONObject versionMap = new JSONObject();
//                            List<String> archList = new ArrayList<String>();
//                            archList.add(arch);
//                            versionMap.put("value", version);
//                            versionMap.put("arch", archList);
//                            versionList.add(versionMap);
                        } else {
                            Boolean flag = true;
                            for (JSONObject versionItem : versionList) {
                                String val = (String) versionItem.get("value");
                                List<String> archList = (List<String>) versionItem.get("arch");
                                if (val.equals(version)) {
                                    archList.add(arch);
                                    flag = false;
                                }
                            }
                            if (flag) {
                                getList(versionList, version, arch);
//                                JSONObject versionMap = new JSONObject();
//                                List<String> archList = new ArrayList<String>();
//                                archList.add(arch);
//                                versionMap.put("value", version);
//                                versionMap.put("arch", archList);
//                                versionList.add(versionMap);
                            }
                        }
                    }
                }
            }
            jsonObj = MyUtil.getJson("成功", 200, resList);
        } else {
            jsonObj = MyUtil.getJson("系统文件不存在。", 606, null);
        }
        return jsonObj;
    }

    /**
     * 增加
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("post")
    public JSONObject post(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
        String host_ids = MyUtil.getRandomString();
        String ip = (String) json.get("ip");
        String login_name = (String) json.get("login_name");
        String login_pwd = (String) json.get("login_pwd");
        String name = (String) json.get("name");
        int port = (int) Double.parseDouble(json.get("port").toString());
        int status = 0;
        String os_arch = (String) json.get("os_arch");
        String os_type = (String) json.get("os_type");
        String os_version = (String) json.get("os_version");
        String record_hash = "test";
        String sql = "INSERT INTO " + tableName + " (host_ids, name, ip, port, os_type, os_version, os_arch, login_name, login_pwd, status, record_hash) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int effectRow = jdbcTemplate.update(sql, host_ids, name, ip, port, os_type, os_version, os_arch, login_name, login_pwd, status, record_hash);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("添加失败", 606, null);
        return jsonObj;
    }

    /**
     * 修改单个
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("put")
    public JSONObject put(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj;
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = (String) json.get("host_ids");
        String ip = (String) json.get("ip");
        String login_name = (String) json.get("login_name");
        String login_pwd = (String) json.get("login_pwd");
        String name = (String) json.get("name");
//        String os_arch = (String) json.get("os_arch");
//        String os_type = (String) json.get("os_type");
//        String os_version = (String) json.get("os_version");
        int port = (int) Double.parseDouble(json.get("port").toString());
        int status = (int) Double.parseDouble(json.get("status").toString());
        String sql = " UPDATE " + tableName + " SET name = ?, ip = ?, port = ?,login_name = ?,login_pwd = ? where host_ids = ? ";
        int effectRow = jdbcTemplate.update(sql, name, ip, port, login_name, login_pwd, ids);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败，此主机不存在", 606, null);
        return jsonObj;
    }

    /**
     * 删除单个
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("/delete/{ids}")
    public JSONObject delete(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("path = " + request.getRequestURI());
        JSONObject jsonObj;
        String sql = " delete from beeeye_host where host_ids = ? ";
        int effectRow = jdbcTemplate.update(sql, ids);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, null);
        else
            jsonObj = MyUtil.getJson("失败，此主机不存在", 606, "");
        return jsonObj;
    }

    /**
     * 批量删除
     * @param request
     * @param response
     * @return JSONObject
     */
    @RequestMapping("delete")
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObj;
        List<String> lists = MyUtil.getListData(request);
        String sql = " delete from " + tableName + " where ids in ( ";
        String[] params = new String[]{};
        int i = 0;
        for (String item : lists) {
            sql += i == lists.size() - 1 ? "?)" : "?, ";
            params[i] = item;
            i++;
        }
        int effectRow = jdbcTemplate.update(sql, params);
        if (effectRow > 0)
            jsonObj = MyUtil.getJson("成功", 200, "");
        else
            jsonObj = MyUtil.getJson("删除失败", 606, "");
        return jsonObj;
    }
}
