package com.ht.vis.core;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.validate.Validator;
import com.ht.vis.Consts;

/**
 *
 */
public abstract class CoreValidator extends Validator {
	public String getErrorJSON() {
		String msg = getController().getAttrForStr(Consts.REQ_JSON_CODE.fail.name());
		JSONObject jo = new JSONObject();
		jo.put("resCode", Consts.REQ_JSON_CODE.fail.name());
		jo.put("resMsg", msg);
		return jo.toJSONString();
	}
}