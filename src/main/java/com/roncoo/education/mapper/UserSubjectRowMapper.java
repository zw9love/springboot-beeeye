package com.roncoo.education.mapper;

import com.roncoo.education.bean.UserSubject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zenngwei
 * @date 2018/6/25
 */
public class UserSubjectRowMapper  implements RowMapper<UserSubject> {
    @Override
    public UserSubject mapRow(ResultSet resultSet, int i) throws SQLException {
        UserSubject userSubject = new UserSubject();
        userSubject.setIds(resultSet.getString("ids"));
        userSubject.setUsername(resultSet.getString("username"));
        userSubject.setCert_sn(resultSet.getString("cert_sn"));
        userSubject.setLogin_name(resultSet.getString("login_name"));
        userSubject.setDept(resultSet.getString("dept"));
        userSubject.setContact(resultSet.getString("contact"));
        userSubject.setMail(resultSet.getString("mail"));
        userSubject.setSens_value(resultSet.getInt("sens_value"));
        userSubject.setReli_value(resultSet.getInt("reli_value"));
        userSubject.setStatus(resultSet.getInt("status"));
        userSubject.setUse_type(resultSet.getInt("use_type"));
        return userSubject;
    }
}
