package com.roncoo.education.mapper;

import com.roncoo.education.bean.SPECSoftWare;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zenngwei
 * @date 2018/6/26
 */
public class SPECSoftWareRowMapper implements RowMapper<SPECSoftWare> {
    @Override
    public SPECSoftWare mapRow(ResultSet resultSet, int i) throws SQLException {
        SPECSoftWare specSoftWare = new SPECSoftWare();
        specSoftWare.setIds(resultSet.getString("ids"));
        specSoftWare.setGroup_name(resultSet.getString("group_name"));
        specSoftWare.setVersion(resultSet.getString("version"));
        specSoftWare.setPlatform_ids(resultSet.getString("platform_ids"));
        specSoftWare.setHash(resultSet.getString("hash"));
        return specSoftWare;
    }
}
