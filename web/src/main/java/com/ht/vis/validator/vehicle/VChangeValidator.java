package com.ht.vis.validator.vehicle;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.VChange;
import com.jfinal.core.Controller;

import java.util.List;


public class VChangeValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        VChange vChange = controller.getModel(VChange.class, "", true);
        String ak = getActionKey();
        List<VChange> list = null;
        if (ak.contains("vChange/save") || ak.contains("vChange/update")) {

            if (StrUtil.isBlank(vChange.getBHost())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "前车主值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(vChange.getBHost())) {
                int len = StrUtil.totalLength(vChange.getBHost());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "前车主长度不能大于50");
                    return;
                }
            }
            if (StrUtil.isBlank(vChange.getLicensePlate())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(vChange.getLicensePlate())) {
                int len = StrUtil.totalLength(vChange.getLicensePlate());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号长度不能大于50");
                    return;
                }
            }
            if (StrUtil.isBlank(vChange.getAHost())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "现车主值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(vChange.getAHost())) {
                int len = StrUtil.totalLength(vChange.getAHost());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "现车主长度不能大于50");
                    return;
                }
            }

            if (StrUtil.isNotBlank(vChange.getATel())) {
                int len = StrUtil.totalLength(vChange.getATel());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "现联系电话长度不能大于50");
                    return;
                }
            }

            if (StrUtil.isNotBlank(vChange.getBTel())) {
                int len = StrUtil.totalLength(vChange.getBTel());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "前联系电话长度不能大于50");
                    return;
                }
            }

            if (vChange.getPrice() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "价格值不能为空");
                return;
            }

            if (vChange.getFee() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "手续费值不能为空");
                return;
            }


        } else if (ak.contains("vChange/del") || ak.contains("vChange/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("vChange/save")) {
        } else if (ak.contains("vChange/update")) {
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
