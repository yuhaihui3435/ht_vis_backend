package com.ht.vis.validator.user;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.User;
import java.util.List;


public class UserValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        User user = controller.getModel(User.class, "", true);
        String ak = getActionKey();
        List<User> list = null;
        if (ak.contains("user/save") || ak.contains("user/update")) {

            if (StrUtil.isBlank(user.getLoginname())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "登录账号值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(user.getLoginname())) {
                int len = StrUtil.totalLength(user.getLoginname());
                if (len < 6) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "登录账号长度不能小于6");
                    return;
                }
            }

            if (StrUtil.isNotBlank(user.getLoginname())) {
                int len = StrUtil.totalLength(user.getLoginname());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "登录账号长度不能大于50");
                    return;
                }
            }

            if (StrUtil.isBlank(user.getNickname())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "昵称值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(user.getNickname())) {
                int len = StrUtil.totalLength(user.getNickname());
                if (len > 50) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "昵称长度不能大于50");
                    return;
                }
            }
            if (StrUtil.isBlank(user.getPhone())) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "电话号值不能为空");
                return;
            }


            if (StrUtil.isNotBlank(user.getPhone())) {
                int len = StrUtil.totalLength(user.getPhone());
                if (len > 20) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "电话号长度不能大于20");
                    return;
                }
            }


            if (StrUtil.isNotBlank(user.getEmail())) {
                int len = StrUtil.totalLength(user.getEmail());
                if (len < 6) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "邮件长度不能小于6");
                    return;
                }
            }

            if (StrUtil.isNotBlank(user.getEmail())) {
                int len = StrUtil.totalLength(user.getEmail());
                if (len > 100) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "邮件长度不能大于100");
                    return;
                }
            }


        } else if (ak.contains("user/del") || ak.contains("user/logicDel")) {
            String ids = controller.getPara("ids");
            if (StrUtil.isBlank(ids)) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if (ak.contains("user/save")) {
            list = User.dao.findByPropEQWithDat("loginname", user.getLoginname());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "登录账号值重复");
                return;
            }
            list = User.dao.findByPropEQWithDat("nickname", user.getNickname());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "昵称值重复");
                return;
            }
            list = User.dao.findByPropEQWithDat("phone", user.getPhone());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "电话号值重复");
                return;
            }
            if (StrUtil.isNotBlank(user.getEmail())) {
                list = User.dao.findByPropEQWithDat("email", user.getEmail());
                if (!list.isEmpty()) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "邮件值重复");
                    return;
                }
            }
        } else if (ak.contains("user/update")) {

            list = User.dao.findByPropEQAndIdNEQWithDat("loginname", user.getLoginname(), user.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "登录账号值重复");
                return;
            }

            list = User.dao.findByPropEQAndIdNEQWithDat("nickname", user.getNickname(), user.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "昵称值重复");
                return;
            }

            list = User.dao.findByPropEQAndIdNEQWithDat("phone", user.getPhone(), user.getId());
            if (!list.isEmpty()) {
                addError(Consts.REQ_JSON_CODE.fail.name(), "电话号值重复");
                return;
            }

            if (StrUtil.isNotBlank(user.getEmail())) {
                list = User.dao.findByPropEQAndIdNEQWithDat("email", user.getEmail(), user.getId());
                if (!list.isEmpty()) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "邮件值重复");
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