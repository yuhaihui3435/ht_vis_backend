package com.ht.vis.validator.ser;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.Ser;

import java.util.List;


public class SerValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        Ser ser = controller.getModel(Ser.class, "", true);
        String ak = getActionKey();
        List<Ser> list = null;
        if (ak.contains("ser/save") || ak.contains("ser/update")) {

            if (StrUtil.isBlank(ser.getCode())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(ser.getCode())) {
                int len = StrUtil.totalLength(ser.getCode());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "编号长度不能大于50");
                    return;
                }
            }
            if (ser.getType() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "类型-0:服务值不能为空");
                return;
            }

            if (StrUtil.isBlank(ser.getName())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "标题值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(ser.getName())) {
                int len = StrUtil.totalLength(ser.getName());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "标题长度不能大于50");
                    return;
                }
            }
            if (StrUtil.isBlank(ser.getUrl())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "URL值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(ser.getUrl())) {
                int len = StrUtil.totalLength(ser.getUrl());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "URL长度不能大于50");
                    return;
                }
            }


        } else if (ak.contains("ser/del") || ak.contains("ser/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("ser/save")) {
            list = Ser.dao.findByPropEQWithDat("code", ser.getCode());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值重复");
                return;
            }
            list = Ser.dao.findByPropEQWithDat("name", ser.getName());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "标题值重复");
                return;
            }
        } else if (ak.contains("ser/update")) {

            list = Ser.dao.findByPropEQAndIdNEQWithDat("code", ser.getCode(), ser.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值重复");
                return;
            }

            list = Ser.dao.findByPropEQAndIdNEQWithDat("name", ser.getName(), ser.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "标题值重复");
                return;
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}