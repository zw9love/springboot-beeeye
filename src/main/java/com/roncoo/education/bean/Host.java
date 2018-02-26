package com.roncoo.education.bean;

import java.io.Serializable;

/**
 * @author zenngwei
 * @date 2018/02/26 17:22
 */
public class Host implements Serializable {
    private String host_ids;
    private String name;
    private String ip;
    private int port;
    private String os_type;
    private String os_version;
    private String os_arch;
    private String login_name;
    private String login_pwd;
    private int status;

    public String getHost_ids() {
        return host_ids;
    }

    public void setHost_ids(String host_ids) {
        this.host_ids = host_ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getOs_type() {
        return os_type;
    }

    public void setOs_type(String os_type) {
        this.os_type = os_type;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getOs_arch() {
        return os_arch;
    }

    public void setOs_arch(String os_arch) {
        this.os_arch = os_arch;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
