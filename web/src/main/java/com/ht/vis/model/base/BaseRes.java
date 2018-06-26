package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseRes<M extends BaseRes<M>> extends CoreModel<M> implements IBean {

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

	public void setUrl(String url) {
		set("url", url);
	}

	public String getUrl() {
		return getStr("url");
	}

	public void setSeq(Integer seq) {
		set("seq", seq);
	}

	public Integer getSeq() {
		return getInt("seq");
	}

	public void setIcon(String icon) {
		set("icon", icon);
	}

	public String getIcon() {
		return getStr("icon");
	}

	public void setLogged(String logged) {
		set("logged", logged);
	}

	public String getLogged() {
		return getStr("logged");
	}

	public void setEnabled(String enabled) {
		set("enabled", enabled);
	}

	public String getEnabled() {
		return getStr("enabled");
	}

	public void setPId(Integer pId) {
		set("pId", pId);
	}

	public Integer getPId() {
		return getInt("pId");
	}

	public void setCode(String code) {
		set("code", code);
	}

	public String getCode() {
		return getStr("code");
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

	public void setLAt(java.util.Date lAt) {
		set("lAt", lAt);
	}

	public java.util.Date getLAt() {
		return get("lAt");
	}

}