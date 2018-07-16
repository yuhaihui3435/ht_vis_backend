package com.ht.vis.validator;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.SFileStorage;

import java.util.List;


public class SFileStorageValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        SFileStorage sFileStorage = controller.getModel(SFileStorage.class, "", true);
        String ak = getActionKey();
        List<SFileStorage> list = null;
        if (ak.equals("/sFileStorage/save") || ak.equals("/sFileStorage/update")) {


            if (StrUtil.isNotBlank(sFileStorage.getTarget())) {
                int len = StrUtil.totalLength(sFileStorage.getTarget());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "存储目标数据(表名+_id)长度不能大于100");
                    return;
                }
            }

            if (StrUtil.isNotBlank(sFileStorage.getType())) {
                int len = StrUtil.totalLength(sFileStorage.getType());
                if (len > 20) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "分类名称长度不能大于20");
                    return;
                }
            }


            if (StrUtil.isNotBlank(sFileStorage.getName())) {
                int len = StrUtil.totalLength(sFileStorage.getName());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "文件名长度不能大于100");
                    return;
                }
            }

            if (StrUtil.isNotBlank(sFileStorage.getFileType())) {
                int len = StrUtil.totalLength(sFileStorage.getFileType());
                if (len > 20) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "文件类型长度不能大于20");
                    return;
                }
            }

            if (StrUtil.isNotBlank(sFileStorage.getCode())) {
                int len = StrUtil.totalLength(sFileStorage.getCode());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "文件编号长度不能大于100");
                    return;
                }
            }
        } else if (ak.equals("/sFileStorage/del") || ak.contains("/sFileStorage/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.equals("/sFileStorage/save")) {
        } else if (ak.equals("/sFileStorage/update")) {
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
