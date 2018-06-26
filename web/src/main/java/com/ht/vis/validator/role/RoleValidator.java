package com.ht.vis.validator.role;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.Role;

import java.util.List;


public class RoleValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        Role role = controller.getModel(Role.class, "", true);
        String ak = getActionKey();
        List<Role> list = null;
        if (ak.contains("role/save") || ak.contains("role/update")) {

            if (StrUtil.isBlank(role.getName())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "角色名值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(role.getName())) {
                int len = StrUtil.totalLength(role.getName());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "角色名长度不能大于50");
                    return;
                }
            }
            if (StrUtil.isBlank(role.getCode())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(role.getCode())) {
                int len = StrUtil.totalLength(role.getCode());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "编号长度不能大于50");
                    return;
                }
            }


        } else if (ak.contains("role/del") || ak.contains("role/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("role/save")) {
            list = Role.dao.findByPropEQWithDat("name", role.getName());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "角色名值重复");
                return;
            }
            list = Role.dao.findByPropEQWithDat("code", role.getCode());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值重复");
                return;
            }
        } else if (ak.contains("role/update")) {

            list = Role.dao.findByPropEQAndIdNEQWithDat("name", role.getName(), role.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "角色名值重复");
                return;
            }

            list = Role.dao.findByPropEQAndIdNEQWithDat("code", role.getCode(), role.getId());
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