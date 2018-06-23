package com.roncoo.education.mapper;

import com.roncoo.education.bean.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setIds(resultSet.getString("ids"));
        user.setLogin_name(resultSet.getString("login_name"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setStatus(resultSet.getString("status"));
        user.setZh_names(resultSet.getString("zh_names"));
//        user.setRole_ids(resultSet.getString("role_ids"));
        return user;
    }
}
