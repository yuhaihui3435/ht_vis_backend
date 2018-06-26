package com.ht.vis.model;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.ht.vis.Consts;
import com.ht.vis.model.base.BaseRes;
import com.ht.vis.kits._SqlKit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Res extends BaseRes<Res> {
	public static final Res dao = new Res().dao();

	public String getLoggedStr(){
		return StrUtil.isBlank(getLogged())?Consts.NON_SET:(getLogged().equals(Consts.YORN_STR.no.getVal())?Consts.YORN_STR.no.getLabel():Consts.YORN_STR.yes.getLabel());
	}
	public String getEnabledStr(){
		return StrUtil.isBlank(getEnabled())?Consts.NON_SET:(getEnabled().equals(Consts.YORN_STR.no.getVal())?Consts.YORN_STR.no.getLabel():Consts.YORN_STR.yes.getLabel());
	}

	public List<Res> getChildren(){
		String sql="select * from s_res where pId=? and dAt is null";
		List<Res> resList=dao.find(sql,getId());
		return (resList!=null&&resList.isEmpty())?null:resList;
	}

	public String getLabel(){
		return getName();
	}


}