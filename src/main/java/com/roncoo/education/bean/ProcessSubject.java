package com.roncoo.education.bean;

/**
 * @author zenngwei
 * @date 2018/6/25
 */
public class ProcessSubject {
    private String ids;
    private String name;
    private String path;
    private String hash;
    private int sens_value;
    private int reli_value;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getSens_value() {
        return sens_value;
    }

    public void setSens_value(int sens_value) {
        this.sens_value = sens_value;
    }

    public int getReli_value() {
        return reli_value;
    }

    public void setReli_value(int reli_value) {
        this.reli_value = reli_value;
    }
}
