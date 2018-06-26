package com.ht.vis.validator.res;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.Res;

import java.util.List;


public class ResValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        Res res = controller.getModel(Res.class, "", true);
        String ak = getActionKey();
        List<Res> list = null;
        if (ak.contains("res/save") || ak.contains("res/update")) {

            if (StrUtil.isBlank(res.getName())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "资源名值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(res.getName())) {
                int len = StrUtil.totalLength(res.getName());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "资源名长度不能大于100");
                    return;
                }
            }


            if (StrUtil.isNotBlank(res.getUrl())) {
                int len = StrUtil.totalLength(res.getUrl());
                if (len > 255) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "资源url长度不能大于255");
                    return;
                }
            }


            if (StrUtil.isNotBlank(res.getIcon())) {
                int len = StrUtil.totalLength(res.getIcon());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "图标长度不能大于50");
                    return;
                }
            }

            if (res.getEnabled() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "是否可用-y:是,n:否值不能为空");
                return;
            }


            if (StrUtil.isBlank(res.getCode())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(res.getCode())) {
                int len = StrUtil.totalLength(res.getCode());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "编号长度不能大于50");
                    return;
                }
            }


        } else if (ak.contains("res/del") || ak.contains("res/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("res/save")) {
            list = Res.dao.findByPropEQWithDat("name", res.getName());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "资源名值重复");
                return;
            }
            list = Res.dao.findByPropEQWithDat("code", res.getCode());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值重复");
                return;
            }
        } else if (ak.contains("res/update")) {

            list = Res.dao.findByPropEQAndIdNEQWithDat("name", res.getName(), res.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "资源名值重复");
                return;
            }

            list = Res.dao.findByPropEQAndIdNEQWithDat("code", res.getCode(), res.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值重复");
                return;
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}