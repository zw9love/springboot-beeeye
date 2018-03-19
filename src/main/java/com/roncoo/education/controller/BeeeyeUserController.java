package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.PageParam;
import com.roncoo.education.bean.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.roncoo.education.util.MyUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/user")
public class BeeeyeUserController {
    //    @PostMapping("get")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("get")
    public JSONObject get() throws JSONException {
        String select = " SELECT * FROM common_user ";
        List<User> list = (List<User>) jdbcTemplate.query(select, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
//                System.out.println("index = " + i);
                User user = new User();
                user.setIds(resultSet.getString("ids"));
                user.setLoginName(resultSet.getString("login_name"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setStatus(resultSet.getString("status"));
                user.setRoleIds(resultSet.getString("role_ids"));
                return user;
            }
        });
        JSONObject jsonObj = MyUtil.getJson("成功", 200, list);
        return jsonObj;
    }

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    private static String resource = "mybatis.cfg.xml";

    static {
        try {
            reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }


    @RequestMapping("/get/{ids}")
    public JSONObject getById(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = session.selectOne("selectUserByID", ids);
            session.commit();
            return MyUtil.getJson("成功", 200, user);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtil.getJson("失败", 606, null);
        } finally {
            session.close();
        }
    }

    @RequestMapping("/put")
    public JSONObject put(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getString(json, "ids");
        String username = MyUtil.getString(json, "username");
        String email = MyUtil.getString(json, "email");
        SqlSession session = sqlSessionFactory.openSession();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("email", email);
        params.put("ids", ids);
        try {
            session.update("updateUser", params);
            session.commit();
            return MyUtil.getJson("成功", 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtil.getJson("失败", 606, null);
        } finally {
            session.close();
        }
    }

    @RequestMapping("/delete/{ids}")
    public JSONObject deleteById(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.delete("deleteUserByID", ids);
            session.commit();
            return MyUtil.getJson("成功", 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtil.getJson("失败", 606, null);
        } finally {
            session.close();
        }
    }

    @RequestMapping("testget")
    public JSONObject testGet(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        Map<String, Object> json = MyUtil.getJsonData(request);
        Map<String, Object> page = (Map<String, Object>) json.get("page");
        int pageNumber = MyUtil.getInt(page, "pageNumber");
        int pageSize = MyUtil.getInt(page, "pageSize");
        int pageStart = (pageNumber - 1) * pageSize;
        // 1、map
        Map<String, Object> pageParam = new HashMap<String, Object>();
        pageParam.put("pageSize", pageSize);
        pageParam.put("pageStart", pageStart);
        // 2、javabean
//        PageParam pageParam = new PageParam();
//        pageParam.setPageStart(pageStart);
//        pageParam.setPageSize(pageSize);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<User> list = session.selectList("selectUserPage", pageParam);
            session.commit();
            return MyUtil.getJson("成功", 200, list);
        } catch (Exception e) {
            e.printStackTrace();
            return MyUtil.getJson("失败", 606, null);
        } finally {
            session.close();
        }
    }
}
