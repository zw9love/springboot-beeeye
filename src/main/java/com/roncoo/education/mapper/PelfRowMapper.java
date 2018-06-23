package com.roncoo.education.mapper;

import com.roncoo.education.bean.Pelf;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PelfRowMapper implements RowMapper<Pelf> {
    @Override
    public Pelf mapRow(ResultSet resultSet, int i) throws SQLException {
        Pelf pelf = new Pelf();
        pelf.setIds(resultSet.getString("ids"));
        pelf.setMd5(resultSet.getString("md5"));
        pelf.setHost_ids(resultSet.getString("host_ids"));
        pelf.setGroup_ids(resultSet.getString("group_ids"));
        pelf.setFile_short_name(resultSet.getString("file_short_name"));
        pelf.setFull_path(resultSet.getString("full_path"));
        pelf.setOriginal_name(resultSet.getString("original_name"));
        pelf.setVersion(resultSet.getString("version"));
        pelf.setPelfstatus(resultSet.getString("pelfstatus"));
        pelf.setAnalysis_flag(resultSet.getString("analysis_flag"));
        pelf.setC_time(resultSet.getInt("c_time"));
        pelf.setM_time(resultSet.getInt("m_time"));
        pelf.setV_time(resultSet.getInt("v_time"));
        pelf.setWhite_type(resultSet.getInt("white_type"));
        return pelf;
    }
}
