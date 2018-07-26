package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.SPECSoftWare;
import com.roncoo.education.mapper.SPECSoftWareRowMapper;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author zenngwei
 * @date 2018/6/26
 */

@RestController
@RequestMapping("/SoftWare_SPEC/updown")
public class BeeeyeSPECSoftWareController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName = "common_software_group";

    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request) {
        String select = " SELECT * FROM  " + tableName;
        String where = " where platform_ids = ? ";
        String count = " SELECT count(*) FROM  " + tableName;
        String pageSql = " limit ?, ? ";
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        Map<String, Object> row = (Map<String, Object>) json.get("row");
        String platformIds = row.get("platformIds").toString();
        int pageNumber = (int) Double.parseDouble(page.get("pageNumber").toString());
        int pageSize = (int) Double.parseDouble(page.get("pageSize").toString());
        int pageStart = (pageNumber - 1) * pageSize;
        Object[] params = new Object[]{platformIds, pageStart, pageSize};
        Object[] countParams = new Object[]{platformIds};
        List<SPECSoftWare> list = jdbcTemplate.query(select + where + pageSql, params, new SPECSoftWareRowMapper());
        // 获取总数
        Integer totalRow = jdbcTemplate.queryForObject(count + where, countParams, Integer.class);
        int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);
        JSONObject resObj = MyUtil.getPageJson(list, pageNumber, pageSize, totalPage, totalRow);
        JSONObject jsonObj = MyUtil.getJson("成功", 200, resObj);
        return jsonObj;
    }

    //处理文件上传
    @RequestMapping("post")
    public JSONObject post(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        JSONObject jsonObj;
        String fileName = file.getOriginalFilename();
        String platform_ids = request.getParameter("platform_ids");
        String group_name = request.getParameter("group_name");
        String version = request.getParameter("version");
//        System.out.println("fileName = " + fileName);
//        System.out.println("platform_ids = " + platform_ids);
//        System.out.println("group_name = " + group_name);
//        System.out.println("version = " + version);
//        String contentType = file.getContentType();
//        System.out.println("getContentType = " + contentType);
        // 修改上传路径 会放在/target/spring-boot-beeeye-1.0.0-SNAPSHOT/upload文件夹
        String filePath = request.getSession().getServletContext().getRealPath("upload/");
//        System.out.println("filePath = " + filePath);
        try {
            MyUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        String ids = MyUtil.getRandomString();
        String sql = " INSERT INTO " + tableName + " (ids, platform_ids, group_name, version, hash) VALUES ( ?, ?, ?, ?, 'add')";
        int effectRow = jdbcTemplate.update(sql, ids, platform_ids, group_name, version);
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
            jsonObj = MyUtil.getJson("删除失败", 606, null);
        return jsonObj;
    }
}
