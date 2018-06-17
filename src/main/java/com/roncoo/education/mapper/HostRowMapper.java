package com.roncoo.education.mapper;

import com.roncoo.education.bean.Host;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HostRowMapper implements RowMapper<Host> {
    @Override
    public Host mapRow(ResultSet resultSet, int i) throws SQLException {
//        获取结果集中的数据
        Host host = new Host();
        host.setHost_ids(resultSet.getString("host_ids"));
        host.setName(resultSet.getString("name"));
        host.setIp(resultSet.getString("ip"));
        host.setPort(resultSet.getInt("port"));
        host.setOs_type(resultSet.getString("os_type"));
        host.setOs_version(resultSet.getString("os_version"));
        host.setOs_arch(resultSet.getString("os_arch"));
        host.setLogin_name(resultSet.getString("login_name"));
        host.setLogin_pwd(resultSet.getString("login_pwd"));
        host.setStatus(resultSet.getInt("status"));
        return host;
    }
}
