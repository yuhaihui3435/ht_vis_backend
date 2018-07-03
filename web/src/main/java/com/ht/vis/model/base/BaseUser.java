package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;


/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends CoreModel<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
	}

	public void setLoginname(String loginname) {
		set("loginname", loginname);
	}

	public String getLoginname() {
		return getStr("loginname");
	}

	public void setPassword(String password) {
		set("password", password);
	}

	public String getPassword() {
		return getStr("password");
	}

	public void setNickname(String nickname) {
		set("nickname", nickname);
	}

	public String getNickname() {
		return getStr("nickname");
	}

	public void setPhone(String phone) {
		set("phone", phone);
	}

	public String getPhone() {
		return getStr("phone");
	}

	public void setEmail(String email) {
		set("email", email);
	}

	public String getEmail() {
		return getStr("email");
	}

	public void setAvatar(String avatar) {
		set("avatar", avatar);
	}

	public String getAvatar() {
		return getStr("avatar");
	}

	public void setStatus(String status) {
		set("status", status);
	}

	public String getStatus() {
		return getStr("status");
	}

	public void setSalt(String salt) {
		set("salt", salt);
	}

	public String getSalt() {
		return getStr("salt");
	}

	public void setLAt(java.util.Date lAt) {
		set("lAt", lAt);
	}

	public java.util.Date getLAt() {
		return get("lAt");
	}

	public void setOpId(Integer opId) {
		set("opId", opId);
	}

	public Integer getOpId() {
		return getInt("opId");
	}

	public void setCAt(java.util.Date cAt) {
		set("cAt", cAt);
	}

	public java.util.Date getCAt() {
		return get("cAt");
	}

	public void setDAt(java.util.Date dAt) {
		set("dAt", dAt);
	}

	public java.util.Date getDAt() {
		return get("dAt");
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		set("lastLoginTime", lastLoginTime);
	}

	public java.util.Date getLastLoginTime() {
		return get("lastLoginTime");
	}

	public void setLastLoginIp(String lastLoginIp) {
		set("lastLoginIp", lastLoginIp);
	}

	public String getLastLoginIp() {
		return getStr("lastLoginIp");
	}


	public void setLoginTime(java.util.Date loginTime) {
		set("loginTime", loginTime);
	}

	public java.util.Date getLoginTime() {
		return get("loginTime");
	}

	public void setLoginIp(String loginIp) {
		set("loginIp", loginIp);
	}

	public String getLoginIp() {
		return getStr("loginIp");
	}

	public void setCCode(String cCode) {
		set("cCode", cCode);
	}

	public String getCCode() {
		return getStr("cCode");
	}

}