package com.ht.vis.validator.vehicle;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.VInfo;
import java.util.List;


public class VInfoValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        VInfo vInfo=controller.getModel(VInfo.class,"",true);
        String ak=getActionKey();
        List<VInfo> list=null;
        if(ak.contains("vInfo/save")||ak.contains("vInfo/update")){

                        if(StrUtil.isBlank(vInfo.getLicensePlate())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(vInfo.getLicensePlate())) {
                                                   int len=StrUtil.totalLength(vInfo.getLicensePlate());
                                                   if(len>20){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号长度不能大于20");
                                                        return;
                                                   }
                                             }
                        if(StrUtil.isBlank(vInfo.getHost())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "车主值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(vInfo.getHost())) {
                                                   int len=StrUtil.totalLength(vInfo.getHost());
                                                   if(len>50){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "车主长度不能大于50");
                                                        return;
                                                   }
                                             }
                    if(vInfo.getType()==null){
                            addError(Consts.REQ_JSON_CODE.fail.name(), "类型值不能为空");
                        return;
                    }

                        if(StrUtil.isBlank(vInfo.getTel())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "手机值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(vInfo.getTel())) {
                                                   int len=StrUtil.totalLength(vInfo.getTel());
                                                   if(len>15){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "手机长度不能大于15");
                                                        return;
                                                   }
                                             }

                                             if (StrUtil.isNotBlank(vInfo.getTelx())) {
                                                   int len=StrUtil.totalLength(vInfo.getTelx());
                                                   if(len>15){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "备用手机长度不能大于15");
                                                        return;
                                                   }
                                             }



                    if(vInfo.getRegDate()==null){
                            addError(Consts.REQ_JSON_CODE.fail.name(), "注册时间值不能为空");
                        return;
                    }





                                             if (StrUtil.isNotBlank(vInfo.getIdcard())) {
                                                   int len=StrUtil.totalLength(vInfo.getIdcard());
                                                   if(len>50){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "身份证号长度不能大于50");
                                                        return;
                                                   }
                                             }
        }else if(ak.contains("vInfo/del")||ak.contains("vInfo/logicDel")){
            String ids =controller.getPara("ids");
            if(StrUtil.isBlank(ids)){
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if(ak.contains("vInfo/save")){
                       list=VInfo.dao.findByPropEQWithDat("licensePlate",vInfo.getLicensePlate());
                       if (!list.isEmpty()) {
                             addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号值重复");
                             return;
                       }
        }else if(ak.contains("vInfo/update")){

                                   list=VInfo.dao.findByPropEQAndIdNEQWithDat("licensePlate",vInfo.getLicensePlate(),vInfo.getId());
                                   if (!list.isEmpty()) {
                                         addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号值重复");
                                         return;
                                   }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
