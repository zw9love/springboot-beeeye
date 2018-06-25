package com.roncoo.education.mapper;

import com.roncoo.education.bean.ObjectLabel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zenngwei
 * @date 2018/6/25
 */
public class ObjectLabelRowMapper implements RowMapper<ObjectLabel> {
    @Override
    public ObjectLabel mapRow(ResultSet resultSet, int i) throws SQLException {
        ObjectLabel objectLabel = new ObjectLabel();
        objectLabel.setIds(resultSet.getString("ids"));
        objectLabel.setName(resultSet.getString("name"));
        objectLabel.setPath(resultSet.getString("path"));
        objectLabel.setSerial(resultSet.getString("serial"));
        objectLabel.setResponsible(resultSet.getString("responsible"));
        objectLabel.setCapacity(resultSet.getString("capacity"));
        objectLabel.setType(resultSet.getInt("type"));
        objectLabel.setSens_value(resultSet.getInt("sens_value"));
        objectLabel.setReli_value(resultSet.getInt("reli_value"));
        return objectLabel;
    }
}
