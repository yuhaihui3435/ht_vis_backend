package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseCDepartmentStaff<M extends BaseCDepartmentStaff<M>> extends CoreModel<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
	}

	public void setDCode(String dCode) {
		set("dCode", dCode);
	}

	public String getDCode() {
		return getStr("dCode");
	}

	public void setSCode(String sCode) {
		set("sCode", sCode);
	}

	public String getSCode() {
		return getStr("sCode");
	}

	public void setJobId(Integer jobId) {
		set("jobId", jobId);
	}

	public Integer getJobId() {
		return getInt("jobId");
	}

	public void setHead(String head) {
		set("head", head);
	}

	public String getHead() {
		return getStr("head");
	}

	public void setCCode(String cCode) {
		set("cCode", cCode);
	}

	public String getCCode() {
		return getStr("cCode");
	}

}
