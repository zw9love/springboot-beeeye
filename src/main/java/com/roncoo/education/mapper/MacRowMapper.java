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
        mac.setIds(resultSet.getString("ids"));
        mac.setHost_ids(resultSet.getString("host_ids"));
        mac.setSub_ids(resultSet.getString("sub_ids"));
        mac.setObj_ids(resultSet.getString("obj_ids"));
        mac.setSubject(resultSet.getString("subject"));
        mac.setObject(resultSet.getString("object"));
        mac.setPrivilege(resultSet.getInt("privilege"));
        mac.setType(resultSet.getInt("type"));
        return mac;
    }
}
