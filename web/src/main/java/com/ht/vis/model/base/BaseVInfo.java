package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseVInfo<M extends BaseVInfo<M>> extends CoreModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setLicensePlate(java.lang.String licensePlate) {
		set("licensePlate", licensePlate);
	}

	public java.lang.String getLicensePlate() {
		return getStr("licensePlate");
	}

	public void setHost(java.lang.String host) {
		set("host", host);
	}

	public java.lang.String getHost() {
		return getStr("host");
	}

	public void setType(java.lang.String type) {
		set("type", type);
	}

	public java.lang.String getType() {
		return getStr("type");
	}

	public void setTel(java.lang.String tel) {
		set("tel", tel);
	}

	public java.lang.String getTel() {
		return getStr("tel");
	}

	public void setTelx(java.lang.String telx) {
		set("telx", telx);
	}

	public java.lang.String getTelx() {
		return getStr("telx");
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

	public void setLAt(java.util.Date lAt) {
		set("lAt", lAt);
	}

	public java.util.Date getLAt() {
		return get("lAt");
	}

	public void setRegDate(java.util.Date regDate) {
		set("regDate", regDate);
	}

	public java.util.Date getRegDate() {
		return get("regDate");
	}

	public void setLine(java.lang.Integer line) {
		set("line", line);
	}

	public java.lang.Integer getLine() {
		return getInt("line");
	}

	public void setArea(java.lang.Integer area) {
		set("area", area);
	}

	public java.lang.Integer getArea() {
		return getInt("area");
	}

	public void setOpId(java.lang.Integer opId) {
		set("opId", opId);
	}

	public java.lang.Integer getOpId() {
		return getInt("opId");
	}

	public void setIdcard(java.lang.String idcard) {
		set("idcard", idcard);
	}

	public java.lang.String getIdcard() {
		return getStr("idcard");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}

	public java.lang.String getStatus() {
		return getStr("status");
	}

}
