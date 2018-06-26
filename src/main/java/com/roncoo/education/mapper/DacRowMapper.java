package com.roncoo.education.mapper;

import com.roncoo.education.bean.Dac;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author zenngwei
 * @date 2018/6/26
 */
public class DacRowMapper implements RowMapper<Dac> {
    @Override
    public Dac mapRow(ResultSet resultSet, int i) throws SQLException {
        Dac dac = new Dac();
        dac.setIds(resultSet.getString("ids"));
        dac.setHost_ids(resultSet.getString("host_ids"));
        dac.setSub_ids(resultSet.getString("sub_ids"));
        dac.setObj_ids(resultSet.getString("obj_ids"));
        dac.setSubject(resultSet.getString("subject"));
        dac.setObject(resultSet.getString("object"));
        dac.setPrivilege(resultSet.getInt("privilege"));
        return dac;
    }
}
