package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseCMeeting<M extends BaseCMeeting<M>> extends CoreModel<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
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

	public void setTitle(String title) {
		set("title", title);
	}

	public String getTitle() {
		return getStr("title");
	}

	public void setHost(String host) {
		set("host", host);
	}

	public String getHost() {
		return getStr("host");
	}

	public void setContent(String content) {
		set("content", content);
	}

	public String getContent() {
		return getStr("content");
	}

	public void setType(String type) {
		set("type", type);
	}

	public String getType() {
		return getStr("type");
	}

	public void setNum(Integer num) {
		set("num", num);
	}

	public Integer getNum() {
		return getInt("num");
	}

	public void setMAt(java.util.Date mAt) {
		set("mAt", mAt);
	}

	public java.util.Date getMAt() {
		return get("mAt");
	}

}
