package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseVChange<M extends BaseVChange<M>> extends CoreModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setBHost(java.lang.String bHost) {
		set("bHost", bHost);
	}

	public java.lang.String getBHost() {
		return getStr("bHost");
	}

	public void setLicensePlate(java.lang.String licensePlate) {
		set("licensePlate", licensePlate);
	}

	public java.lang.String getLicensePlate() {
		return getStr("licensePlate");
	}

	public void setAHost(java.lang.String aHost) {
		set("aHost", aHost);
	}

	public java.lang.String getAHost() {
		return getStr("aHost");
	}

	public void setATel(java.lang.String aTel) {
		set("aTel", aTel);
	}

	public java.lang.String getATel() {
		return getStr("aTel");
	}

	public void setBTel(java.lang.String bTel) {
		set("bTel", bTel);
	}

	public java.lang.String getBTel() {
		return getStr("bTel");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public void setPrice(java.math.BigDecimal price) {
		set("price", price);
	}

	public java.math.BigDecimal getPrice() {
		return get("price");
	}

	public void setFee(java.math.BigDecimal fee) {
		set("fee", fee);
	}

	public java.math.BigDecimal getFee() {
		return get("fee");
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

	public void setOpId(java.lang.Integer opId) {
		set("opId", opId);
	}

	public java.lang.Integer getOpId() {
		return getInt("opId");
	}

	public void setChangeDate(java.util.Date changeDate) {
		set("changeDate", changeDate);
	}

	public java.util.Date getChangeDate() {
		return get("changeDate");
	}

	public void setCCode(String cCode) {
		set("cCode", cCode);
	}

	public String getCCode() {
		return getStr("cCode");
	}

}
