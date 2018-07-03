package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseCInfo<M extends BaseCInfo<M>> extends CoreModel<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getName() {
		return getStr("name");
	}

	public void setCode(String code) {
		set("code", code);
	}

	public String getCode() {
		return getStr("code");
	}

	public void setRetDate(java.util.Date retDate) {
		set("retDate", retDate);
	}

	public java.util.Date getRetDate() {
		return get("retDate");
	}

	public void setCNo(String cNo) {
		set("cNo", cNo);
	}

	public String getCNo() {
		return getStr("cNo");
	}

	public void setCType(Integer cType) {
		set("cType", cType);
	}

	public Integer getCType() {
		return getInt("cType");
	}

	public void setBType(String bType) {
		set("bType", bType);
	}

	public String getBType() {
		return getStr("bType");
	}

	public void setHead(String head) {
		set("head", head);
	}

	public String getHead() {
		return getStr("head");
	}

	public void setTel(String tel) {
		set("tel", tel);
	}

	public String getTel() {
		return getStr("tel");
	}

	public void setBusNum(Integer busNum) {
		set("busNum", busNum);
	}

	public Integer getBusNum() {
		return getInt("busNum");
	}

	public void setStaffNum(Integer staffNum) {
		set("staffNum", staffNum);
	}

	public Integer getStaffNum() {
		return getInt("staffNum");
	}

	public void setSofficerNum(Integer sofficerNum) {
		set("sofficerNum", sofficerNum);
	}

	public Integer getSofficerNum() {
		return getInt("sofficerNum");
	}

	public void setAddress(String address) {
		set("address", address);
	}

	public String getAddress() {
		return getStr("address");
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

	public void setOpId(Integer opId) {
		set("opId", opId);
	}

	public Integer getOpId() {
		return getInt("opId");
	}

	public void setGrade(java.lang.String grade) {
		set("grade", grade);
	}

	public java.lang.String getGrade() {
		return getStr("grade");
	}

	public void setExpiryDate(java.util.Date expiryDate) {
		set("expiryDate", expiryDate);
	}

	public java.util.Date getExpiryDate() {
		return get("expiryDate");
	}
    public void setAccountExpiryDate(java.util.Date accountExpiryDate) {
        set("accountExpiryDate", accountExpiryDate);
    }

    public java.util.Date getAccountExpiryDate() {
        return get("accountExpiryDate");
    }



}
