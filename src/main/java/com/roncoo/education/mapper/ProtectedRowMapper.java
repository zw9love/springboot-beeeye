package com.roncoo.education.mapper;

import com.roncoo.education.bean.Protected;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zenngwei
 * @date @date
 */
public class ProtectedRowMapper  implements RowMapper<Protected> {
    @Override
    public Protected mapRow(ResultSet resultSet, int i) throws SQLException {
        Protected proc = new Protected();
        proc.setName(resultSet.getString("name"));
        proc.setFull_path(resultSet.getString("full_path"));
        proc.setIds(resultSet.getString("ids"));
        proc.setHost_ids(resultSet.getString("host_ids"));
        proc.setHash(resultSet.getString("hash"));
        proc.setType(resultSet.getInt("type"));
        return proc;
    }
}
