package com.ht.vis.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.ht.vis.core.CoreModel;

/**
 * Generated ap-jf.
 */
@SuppressWarnings("serial")
public abstract class BaseSpAssessmentCriteria<M extends BaseSpAssessmentCriteria<M>> extends CoreModel<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
	}

	public void setNorm(Integer norm) {
		set("norm", norm);
	}

	public Integer getNorm() {
		return getInt("norm");
	}

	public void setTitle(Integer title) {
		set("title", title);
	}

	public Integer getTitle() {
		return getInt("title");
	}

	public void setEssential(String essential) {
		set("essential", essential);
	}

	public String getEssential() {
		return getStr("essential");
	}

	public void setPoint(String point) {
		set("point", point);
	}

	public String getPoint() {
		return getStr("point");
	}

	public void setGrading(String grading) {
		set("grading", grading);
	}

	public String getGrading() {
		return getStr("grading");
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

	public void setStars(String stars) {
		set("stars", stars);
	}

	public String getStars() {
		return getStr("stars");
	}

}
