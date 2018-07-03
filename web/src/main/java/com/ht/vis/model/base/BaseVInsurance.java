package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseVInsurance<M extends BaseVInsurance<M>> extends CoreModel<M> implements IBean {

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

	public void setBAt(java.util.Date bAt) {
		set("bAt", bAt);
	}

	public java.util.Date getBAt() {
		return get("bAt");
	}

	public void setEAt(java.util.Date eAt) {
		set("eAt", eAt);
	}

	public java.util.Date getEAt() {
		return get("eAt");
	}

	public void setCAt(java.util.Date cAt) {
		set("cAt", cAt);
	}

	public java.util.Date getCAt() {
		return get("cAt");
	}

	public void setLAt(java.util.Date lAt) {
		set("lAt", lAt);
	}

	public java.util.Date getLAt() {
		return get("lAt");
	}

	public void setDAt(java.util.Date dAt) {
		set("dAt", dAt);
	}

	public java.util.Date getDAt() {
		return get("dAt");
	}

	public void setNum(java.lang.String num) {
		set("num", num);
	}

	public java.lang.String getNum() {
		return getStr("num");
	}

	public void setPrice(java.math.BigDecimal price) {
		set("price", price);
	}

	public java.math.BigDecimal getPrice() {
		return get("price");
	}

	public void setOpId(java.lang.Integer opId) {
		set("opId", opId);
	}

	public java.lang.Integer getOpId() {
		return getInt("opId");
	}

	public void setCCode(String cCode) {
		set("cCode", cCode);
	}

	public String getCCode() {
		return getStr("cCode");
	}

}
