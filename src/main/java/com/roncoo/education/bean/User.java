/**
 * 2015-2016 龙果学院 (www.roncoo.com)
 */
package com.roncoo.education.bean;


import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * 
 * @author zengwei
 */
public class User implements Serializable {
	private String ids;
	private String loginName;
	private String loginPwd;
	private String username;
	private String email;
	private String phone;
	private String status;
	private String roleIds;
	private String recordHash;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRecordHash() {
		return recordHash;
	}

	public void setRecordHash(String recordHash) {
		this.recordHash = recordHash;
	}
}
