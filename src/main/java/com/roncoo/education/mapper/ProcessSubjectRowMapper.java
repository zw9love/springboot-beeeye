package com.roncoo.education.mapper;

import com.roncoo.education.bean.ProcessSubject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zenngwei
 * @date 2018/6/25
 */
public class ProcessSubjectRowMapper implements RowMapper<ProcessSubject> {
    @Override
    public ProcessSubject mapRow(ResultSet resultSet, int i) throws SQLException {
        ProcessSubject processSubject = new ProcessSubject();
        processSubject.setIds(resultSet.getString("ids"));
        processSubject.setName(resultSet.getString("name"));
        processSubject.setPath(resultSet.getString("path"));
        processSubject.setHash(resultSet.getString("hash"));
        processSubject.setSens_value(resultSet.getInt("sens_value"));
        processSubject.setReli_value(resultSet.getInt("reli_value"));
        return processSubject;
    }
}
