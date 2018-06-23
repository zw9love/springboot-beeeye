package com.roncoo.education.bean;

import java.io.Serializable;

public class Pelf  implements Serializable {
    private String ids;
    private String md5;
    private String sha1;
    private String host_ids;
    private String group_ids;
    private String file_short_name;
    private String full_path;
    private String company;
    private String suffix;
    private String original_name;
    private String version;
    private String pelfstatus;
    private String analysis_flag;
    private int c_time;
    private int m_time;
    private int v_time;
    private int white_type;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getHost_ids() {
        return host_ids;
    }

    public void setHost_ids(String host_ids) {
        this.host_ids = host_ids;
    }

    public String getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(String group_ids) {
        this.group_ids = group_ids;
    }

    public String getFile_short_name() {
        return file_short_name;
    }

    public void setFile_short_name(String file_short_name) {
        this.file_short_name = file_short_name;
    }

    public String getFull_path() {
        return full_path;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPelfstatus() {
        return pelfstatus;
    }

    public void setPelfstatus(String pelfstatus) {
        this.pelfstatus = pelfstatus;
    }

    public String getAnalysis_flag() {
        return analysis_flag;
    }

    public void setAnalysis_flag(String analysis_flag) {
        this.analysis_flag = analysis_flag;
    }

    public int getC_time() {
        return c_time;
    }

    public void setC_time(int c_time) {
        this.c_time = c_time;
    }

    public int getM_time() {
        return m_time;
    }

    public void setM_time(int m_time) {
        this.m_time = m_time;
    }

    public int getV_time() {
        return v_time;
    }

    public void setV_time(int v_time) {
        this.v_time = v_time;
    }

    public int getWhite_type() {
        return white_type;
    }

    public void setWhite_type(int white_type) {
        this.white_type = white_type;
    }
}
