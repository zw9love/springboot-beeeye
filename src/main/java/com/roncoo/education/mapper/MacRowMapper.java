package com.roncoo.education.mapper;

import com.roncoo.education.bean.Mac;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zenngwei
 * @date 2018/6/25
 */
public class MacRowMapper  implements RowMapper<Mac> {
    @Override
    public Mac mapRow(ResultSet resultSet, int i) throws SQLException {
        Mac mac = new Mac();
        return mac;
    }
}
