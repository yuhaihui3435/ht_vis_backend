package com.ht.vis.validator.company;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.CMeeting;
import com.jfinal.core.Controller;

import java.util.List;


public class CMeetingValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        CMeeting cMeeting = controller.getModel(CMeeting.class, "", true);
        String ak = getActionKey();
        List<CMeeting> list = null;
        if (ak.contains("cMeeting/save") || ak.contains("cMeeting/update")) {


            if (StrUtil.isBlank(cMeeting.getTitle())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "主题值不能为空");
                return;
            }

            if (StrUtil.isNotBlank(cMeeting.getTitle())) {
                int len = StrUtil.totalLength(cMeeting.getTitle());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "主题长度不能大于100");
                    return;
                }
            }
            if (cMeeting.getHost() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "主持人值不能为空");
                return;
            }

            if (StrUtil.isBlank(cMeeting.getContent())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "内容值不能为空");
                return;
            }

            if (cMeeting.getType() == null) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "类型值不能为空");
                return;
            }


        } else if (ak.contains("cMeeting/del") || ak.contains("cMeeting/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("cMeeting/save")) {
        } else if (ak.contains("cMeeting/update")) {
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
