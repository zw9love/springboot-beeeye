package com.roncoo.education.bean;

import java.io.Serializable;

public class Integrity implements Serializable {
    private String ids;
    private int type;
    private String name;
    private String full_path;
    private String host_ids;
    private String hash;
    private int status;
    private int time;
    private int file_type;
    
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_path() {
        return full_path;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }

    public String getHost_ids() {
        return host_ids;
    }

    public void setHost_ids(String host_ids) {
        this.host_ids = host_ids;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

}
