package com.roncoo.education.bean;

/**
 * @author zenngwei
 * @date 2018/6/25
 */
public class Mac {
    private String ids;
    private String host_ids;
    private String sub_ids;
    private String obj_ids;
    private int type;
    private int privilege;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getHost_ids() {
        return host_ids;
    }

    public void setHost_ids(String host_ids) {
        this.host_ids = host_ids;
    }

    public String getSub_ids() {
        return sub_ids;
    }

    public void setSub_ids(String sub_ids) {
        this.sub_ids = sub_ids;
    }

    public String getObj_ids() {
        return obj_ids;
    }

    public void setObj_ids(String obj_ids) {
        this.obj_ids = obj_ids;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
}
