package com.roncoo.education.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.bean.Menu;
import com.roncoo.education.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zenngwei
 * @date 2018/02/26 16:06
 */
@RestController
@RequestMapping("/menu")
public class BeeeyeMenuController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("get")
    public JSONObject get() throws JSONException {
        String select = " SELECT * FROM common_menu ";
        List<Menu> list = jdbcTemplate.query(select, new RowMapper<Menu>() {
            @Override
            public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
//                System.out.println("index = " + i);
                Menu menu = new Menu();
                menu.setIds(resultSet.getString("ids"));
                menu.setNames(resultSet.getString("names"));
                menu.setLevel(resultSet.getInt("level"));
                menu.setParent_ids(resultSet.getString("parent_ids"));
                menu.setDescription(resultSet.getString("description"));
                menu.setUrl(resultSet.getString("url"));
                menu.setIcon(resultSet.getString("icon"));
                return menu;
            }
        });
        JSONObject jsonObj = MyUtil.getJson("成功", 200, list);
        return jsonObj;
    }
}
