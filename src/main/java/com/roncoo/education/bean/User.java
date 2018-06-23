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
	private String login_name;
	private String login_pwd;
	private String username;
	private String email;
	private String phone;
	private String status;
	private String role_ids;
	private String record_hash;
	private String zh_names;

	public String getZh_names() {
		return zh_names;
	}

	public void setZh_names(String zh_names) {
		this.zh_names = zh_names;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public String getRole_ids() {
		return role_ids;
	}

	public void setRole_ids(String role_ids) {
		this.role_ids = role_ids;
	}

	public String getRecord_hash() {
		return record_hash;
	}

	public void setRecord_hash(String record_hash) {
		this.record_hash = record_hash;
	}


}
