package com.ht.vis.validator.vehicle;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.VLine;
import com.jfinal.kit.Kv;

import java.util.List;


public class VLineValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        VLine vLine = controller.getModel(VLine.class, "", true);
        String ak = getActionKey();
        List<VLine> list = null;
        if (ak.equals("/vLine/save") || ak.equals("/vLine/update")) {

            if (StrUtil.isBlank(vLine.getCCode())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "企业编号值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(vLine.getCCode())) {
                int len = StrUtil.totalLength(vLine.getCCode());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "企业编号长度不能大于50");
                    return;
                }
            }

            if (StrUtil.isNotBlank(vLine.getName())) {
                int len = StrUtil.totalLength(vLine.getName());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "线路名称长度不能大于100");
                    return;
                }
            }


        } else if (ak.equals("/vLine/del") || ak.contains("/vLine/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        Kv kv=null;
        if (ak.equals("/vLine/save")) {

            if (StrUtil.isNotBlank(vLine.getName())) {
                kv=Kv.create();
                kv.put("name=",vLine.getName());
                kv.put("cCode=",vLine.getCCode());
                kv.put("dAt","");
                kv=VLine.buildParamMap(VLine.class,kv);
                list=VLine.dao.findByAndCond(kv);
//                list = VLine.dao.findByPropEQWithDat("name", vLine.getName());
                if (!list.isEmpty()) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "线路名称值重复");
                    return;
                }
            }
        } else if (ak.equals("/vLine/update")) {

            if (StrUtil.isNotBlank(vLine.getName())) {
                kv=Kv.create();
                kv.put("name=",vLine.getName());
                kv.put("cCode=",vLine.getCCode());
                kv.put("id<>",vLine.getId());
                kv.put("dAt","");
                kv=VLine.buildParamMap(VLine.class,kv);
                list=VLine.dao.findByAndCond(kv);
                list = VLine.dao.findByPropEQAndIdNEQWithDat("name", vLine.getName(), vLine.getId());
                if (!list.isEmpty()) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "线路名称值重复");
                    return;
                }
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
