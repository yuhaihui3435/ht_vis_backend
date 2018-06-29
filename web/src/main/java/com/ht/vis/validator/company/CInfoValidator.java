package com.ht.vis.validator.company;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.CInfo;

import java.util.List;


public class CInfoValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        CInfo cInfo = controller.getModel(CInfo.class, "", true);
        String ak = getActionKey();
        List<CInfo> list = null;
        if (ak.contains("cInfo/save") || ak.contains("cInfo/update")) {

            if (StrUtil.isBlank(cInfo.getName())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "名字值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(cInfo.getName())) {
                int len = StrUtil.totalLength(cInfo.getName());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "名字长度不能大于100");
                    return;
                }
            }
            if (StrUtil.isBlank(cInfo.getCode())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "企业编号值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(cInfo.getCode())) {
                int len = StrUtil.totalLength(cInfo.getCode());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "企业编号长度不能大于50");
                    return;
                }
            }
            if (cInfo.getRetDate() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "注册时间值不能为空");
                return;
            }

            if (StrUtil.isBlank(cInfo.getCNo())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "企业统一代码证号值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(cInfo.getCNo())) {
                int len = StrUtil.totalLength(cInfo.getCNo());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "企业统一代码证号长度不能大于50");
                    return;
                }
            }
            if (cInfo.getCType() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "企业类型值不能为空");
                return;
            }

            if (cInfo.getBType() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "营运类型值不能为空");
                return;
            }

            if (StrUtil.isBlank(cInfo.getHead())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "法人值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(cInfo.getHead())) {
                int len = StrUtil.totalLength(cInfo.getHead());
                if (len > 20) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "法人长度不能大于20");
                    return;
                }
            }
            if (StrUtil.isBlank(cInfo.getTel())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "联系电话值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(cInfo.getAddress())) {
                int len = StrUtil.totalLength(cInfo.getAddress());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "经营地址长度不能大于100");
                    return;
                }
            }


        } else if (ak.contains("cInfo/del") || ak.contains("cInfo/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("cInfo/save")) {
//            list = CInfo.dao.findByPropEQWithDat("name", cInfo.getName());
//            if (!list.isEmpty()) {
//                addError(Consts.REQ_JSON_CODE.fail.name(), "名字值重复");
//                return;
//            }
//            list = CInfo.dao.findByPropEQWithDat("code", cInfo.getCode());
//            if (!list.isEmpty()) {
//                addError(Consts.REQ_JSON_CODE.fail.name(), "企业编号值重复");
//                return;
//            }
        } else if (ak.contains("cInfo/update")) {

            list = CInfo.dao.findByPropEQAndIdNEQWithDat("name", cInfo.getName(), cInfo.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "名字值重复");
                return;
            }

            list = CInfo.dao.findByPropEQAndIdNEQWithDat("code", cInfo.getCode(), cInfo.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "企业编号值重复");
                return;
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
