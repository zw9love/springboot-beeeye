package com.roncoo.education.mapper;

import com.roncoo.education.bean.Integrity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegrityRowMapper  implements RowMapper<Integrity> {
    @Override
    public Integrity mapRow(ResultSet resultSet, int i) throws SQLException {
        Integrity integrity = new Integrity();
        integrity.setIds(resultSet.getString("ids"));
        integrity.setName(resultSet.getString("name"));
        integrity.setFull_path(resultSet.getString("full_path"));
        integrity.setHost_ids(resultSet.getString("host_ids"));
        integrity.setHash(resultSet.getString("hash"));
        integrity.setType(resultSet.getInt("type"));
        integrity.setStatus(resultSet.getInt("status"));
        integrity.setTime(resultSet.getInt("time"));
        integrity.setFile_type(resultSet.getInt("file_type"));
        return integrity;
    }
}
