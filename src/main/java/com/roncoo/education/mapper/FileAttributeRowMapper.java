package com.roncoo.education.mapper;

import com.roncoo.education.bean.FileAttribute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zenngwei
 * @date 2018/6/26
 */
public class FileAttributeRowMapper implements RowMapper<FileAttribute> {
    @Override
    public FileAttribute mapRow(ResultSet resultSet, int i) throws SQLException {
        FileAttribute fileAttribute = new FileAttribute();
        fileAttribute.setIds(resultSet.getString("ids"));
        fileAttribute.setHost_ids(resultSet.getString("host_ids"));
        fileAttribute.setFile_path(resultSet.getString("file_path"));
        fileAttribute.setPrivilege(resultSet.getString("privilege"));
        fileAttribute.setUser_name(resultSet.getString("user_name"));
        fileAttribute.setCreate_time(resultSet.getInt("create_time"));
        return fileAttribute;
    }
}
