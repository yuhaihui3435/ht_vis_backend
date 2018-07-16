package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseSFileStorage<M extends BaseSFileStorage<M>> extends CoreModel<M> implements IBean {

	public void setId(Long id) {
		set("id", id);
	}

	public Long getId() {
		return getLong("id");
	}

	public void setTarget(String target) {
		set("target", target);
	}

	public String getTarget() {
		return getStr("target");
	}

	public void setType(String type) {
		set("type", type);
	}

	public String getType() {
		return getStr("type");
	}

	public void setUrl(String url) {
		set("url", url);
	}

	public String getUrl() {
		return getStr("url");
	}

	public void setCAt(java.util.Date cAt) {
		set("cAt", cAt);
	}

	public java.util.Date getCAt() {
		return get("cAt");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getName() {
		return getStr("name");
	}

	public void setFileType(String fileType) {
		set("fileType", fileType);
	}

	public String getFileType() {
		return getStr("fileType");
	}

	public void setCode(String code) {
		set("code", code);
	}

	public String getCode() {
		return getStr("code");
	}

}
