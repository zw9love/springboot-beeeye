package com.roncoo.education.mapper;

import com.roncoo.education.bean.Menu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuRowMapper implements RowMapper<Menu> {
    @Override
    public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
//        获取结果集中的数据
        Menu menu = new Menu();
        menu.setIds(resultSet.getString("ids"));
        menu.setNames(resultSet.getString("names"));
        menu.setLevel(resultSet.getInt("level"));
        menu.setParent_ids(resultSet.getString("parent_ids"));
//        menu.setDescription(resultSet.getString("description"));
        menu.setUrl(resultSet.getString("url"));
        menu.setIcon(resultSet.getString("icon"));
        return menu;
    }
}
