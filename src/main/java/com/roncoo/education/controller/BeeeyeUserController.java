package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.PageParam;
import com.roncoo.education.bean.User;
import com.roncoo.education.util.MD5Util;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.query.NativeQuery;
//import org.hibernate.query.Query;
//import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.roncoo.education.util.MyUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    // 1、以下是jdbcTemplate获取数据
    @Autowired
    private JdbcTemplate jdbcTemplate;
//    @RequestMapping("get")
//    public JSONObject get() throws JSONException {
//        String select = " SELECT * FROM common_user ";
//        List<User> list = (List<User>) jdbcTemplate.query(select, new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet resultSet, int i) throws SQLException {
////                System.out.println("index = " + i);
//                User user = new User();
//                user.setIds(resultSet.getString("ids"));
//                user.setLoginName(resultSet.getString("login_name"));
//                user.setUsername(resultSet.getString("username"));
//                user.setEmail(resultSet.getString("email"));
//                user.setPhone(resultSet.getString("phone"));
//                user.setStatus(resultSet.getString("status"));
//                user.setRoleIds(resultSet.getString("role_ids"));
//                return user;
//            }
//        });
//        JSONObject jsonObj = MyUtil.getJson("成功", 200, list);
//        return jsonObj;
//    }


    // 2、以下是mybatis获取数据
    @RequestMapping("get")
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) throws JSONException {
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

    @RequestMapping("/post")
    public JSONObject post(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> json = MyUtil.getJsonData(request);
        String ids = MyUtil.getRandomString();
        String username = MyUtil.getString(json, "username");
        String loginName = MyUtil.getString(json, "login_name");
        String loginPwd = MD5Util.encrypt(MyUtil.getString(json, "login_pwd"));
        String email = MyUtil.getString(json, "email");
        SqlSession session = sqlSessionFactory.openSession();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("email", email);
        params.put("loginName", loginName);
        params.put("loginPwd", loginPwd);
        params.put("ids", ids);
        try {
            session.insert("insertUser", params);
//            session.update("updateUser", params);
            session.commit();
            return MyUtil.getJson("成功", 200, null);
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

//    ///3、以下是hibernate获取数据
//    private static SessionFactory sf;
//    static {
//        //创建SessionFactory对象
//        sf = new Configuration().configure().buildSessionFactory();
//    }
//
//    @RequestMapping("testget")
//    public JSONObject testGet(HttpServletRequest request, HttpServletResponse response) throws JSONException {
//        Map<String, Object> json = MyUtil.getJsonData(request);
//        Map<String, Object> page = (Map<String, Object>) json.get("page");
//        int pageNumber = MyUtil.getInt(page, "pageNumber");
//        int pageSize = MyUtil.getInt(page, "pageSize");
//        int pageStart = (pageNumber - 1) * pageSize;
//        Session session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
////        已经过时。。。
////        //把每一行记录封装为对象数组，再添加到list集合
////        NativeQuery nativeQuery = session.createSQLQuery("SELECT * FROM common_user limit ?, ?");
////        nativeQuery.setParameter(0, pageStart);
////        nativeQuery.setParameter(1, pageSize);
////        //把每一行记录封装为指定的对象类型
////        SQLQuery sqlQuery = nativeQuery.addEntity(User.class);
////        List list = sqlQuery.list();
//
////        //把每一行记录封装为对象数组，再添加到list集合
////        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM common_user limit ?, ?");
////        nativeQuery.setParameter(0, pageStart);
////        nativeQuery.setParameter(1, pageSize);
//        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM common_user limit :pageStart, :pageSize");
//        nativeQuery.setParameter("pageStart", pageStart);
//        nativeQuery.setParameter("pageSize", pageSize);
//        //把每一行记录封装为指定的对象类型
//        NativeQuery sqlQuery = nativeQuery.addEntity(User.class);
//        List list = sqlQuery.list();
//
//        //关闭
//        tx.commit();
//        session.close();
//        return MyUtil.getJson("成功", 200, list);
//    }
//
//    @RequestMapping("testget/{ids}")
//    public JSONObject testGetById(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
//        Session session = sf.openSession();
//        User user = new User();
//        user.setIds(ids);
//        Transaction tx = session.beginTransaction();
//        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM common_user where ids = :ids");
//        nativeQuery.setParameter("ids", ids);
//        //把每一行记录封装为指定的对象类型
//        NativeQuery sqlQuery = nativeQuery.addEntity(User.class);
//        List list = sqlQuery.list();
//        tx.commit();
//        session.close();
//        return MyUtil.getJson("成功", 200, list.get(0));
//    }
//
//    @RequestMapping("testdelete/{ids}")
//    public JSONObject testDelete(@PathVariable String ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
//        Session session = sf.openSession();
//        User user = new User();
//        user.setIds(ids);
//        Transaction tx = session.beginTransaction();
//        session.delete(user);
//        tx.commit();
//        session.close();
//        return MyUtil.getJson("成功", 200, null);
//    }
//
//    @RequestMapping("testput")
//    public JSONObject testPut(HttpServletRequest request, HttpServletResponse response) throws JSONException {
//        Map<String, Object> json = MyUtil.getJsonData(request);
//        String ids = MyUtil.getString(json, "ids");
//        String username = MyUtil.getString(json, "username");
//        String email = MyUtil.getString(json, "email");
//        Session session = sf.openSession();
//        User user = new User();
//        user.setIds(ids);
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setStatus("normal");
//        Transaction tx = session.beginTransaction();
//        session.update(user);
//        tx.commit();
//        session.close();
//        return MyUtil.getJson("成功", 200, null);
//    }
//
//    @RequestMapping("testpost")
//    public JSONObject testPost(HttpServletRequest request, HttpServletResponse response) throws JSONException {
//        HttpSession httpSession = request.getSession();
//        String token = request.getHeader("token");
//        Map<String, Object> json = MyUtil.getJsonData(request);
//        String ids = MyUtil.getRandomString();
//        String username = MyUtil.getString(json, "username");
//        String login_name = MyUtil.getString(json, "login_name");
//        String login_pwd = MD5Util.encrypt(MyUtil.getString(json, "login_pwd"));
//        String role_ids;
//        if(token.equals("debug"))
//            role_ids = "0";
//        else{
//            JSONObject role = (JSONObject) httpSession.getAttribute(token);
//            role_ids = (String) role.get("ids");
//        }
//        String email = MyUtil.getString(json, "email");
//        Session session = sf.openSession();
//        User user = new User();
//        user.setIds(ids);
//        user.setLoginName(login_name);
//        user.setLoginPwd(login_pwd);
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setStatus("normal");
//        user.setPhone("");
//        user.setRoleIds(role_ids);
//        user.setRecordHash("");
//        Transaction tx = session.beginTransaction();
//        session.save(user);
//        tx.commit();
//        session.close();
//        return MyUtil.getJson("成功", 200, null);
//    }
}
