package com.ht.vis.validator.company;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.CStaff;
import java.util.List;


public class CStaffValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        CStaff cStaff=controller.getModel(CStaff.class,"",true);
        String ak=getActionKey();
        List<CStaff> list=null;
        if(ak.contains("cStaff/save")||ak.contains("cStaff/update")){

                        if(StrUtil.isBlank(cStaff.getName())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "姓名值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(cStaff.getName())) {
                                                   int len=StrUtil.totalLength(cStaff.getName());
                                                   if(len>50){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "姓名长度不能大于50");
                                                        return;
                                                   }
                                             }
                        if(StrUtil.isBlank(cStaff.getCode())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "编号值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(cStaff.getCode())) {
                                                   int len=StrUtil.totalLength(cStaff.getCode());
                                                   if(len>50){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "编号长度不能大于50");
                                                        return;
                                                   }
                                             }
                        if(StrUtil.isBlank(cStaff.getTel())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "手机号值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(cStaff.getTel())) {
                                                   int len=StrUtil.totalLength(cStaff.getTel());
                                                   if(len>50){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "手机号长度不能大于50");
                                                        return;
                                                   }
                                             }



        }else if(ak.contains("cStaff/del")||ak.contains("cStaff/logicDel")){
            String ids =controller.getPara("ids");
            if(StrUtil.isBlank(ids)){
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if(ak.contains("cStaff/save")){
                       list=CStaff.dao.findByPropEQWithDat("code",cStaff.getCode());
                       if (!list.isEmpty()) {
                             addError(Consts.REQ_JSON_CODE.fail.name(), "编号值重复");
                             return;
                       }
        }else if(ak.contains("cStaff/update")){

                                   list=CStaff.dao.findByPropEQAndIdNEQWithDat("code",cStaff.getCode(),cStaff.getId());
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
