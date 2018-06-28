package com.ht.vis.validator.company;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.CDepartment;

import java.util.List;


public class CDepartmentValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        CDepartment cDepartment = controller.getModel(CDepartment.class, "", true);
        String ak = getActionKey();
        List<CDepartment> list = null;
        if (ak.contains("cDepartment/save") || ak.contains("cDepartment/update")) {

            if (StrUtil.isBlank(cDepartment.getName())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "名称值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(cDepartment.getName())) {
                int len = StrUtil.totalLength(cDepartment.getName());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "名称长度不能大于50");
                    return;
                }
            }
            if (StrUtil.isBlank(cDepartment.getCode())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(cDepartment.getCode())) {
                int len = StrUtil.totalLength(cDepartment.getCode());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "编号长度不能大于50");
                    return;
                }
            }


        } else if (ak.contains("cDepartment/del") || ak.contains("cDepartment/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("cDepartment/save")) {
            list = CDepartment.dao.findByPropEQWithDat("name", cDepartment.getName());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "名称值重复");
                return;
            }
            list = CDepartment.dao.findByPropEQWithDat("code", cDepartment.getCode());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "编号值重复");
                return;
            }
        } else if (ak.contains("cDepartment/update")) {

            list = CDepartment.dao.findByPropEQAndIdNEQWithDat("name", cDepartment.getName(), cDepartment.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "名称值重复");
                return;
            }

            list = CDepartment.dao.findByPropEQAndIdNEQWithDat("code", cDepartment.getCode(), cDepartment.getId());
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
