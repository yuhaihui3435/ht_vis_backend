package com.ht.vis.validator.vehicle;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.VInsurance;
import java.util.List;


public class VInsuranceValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        VInsurance vInsurance=controller.getModel(VInsurance.class,"",true);
        String ak=getActionKey();
        List<VInsurance> list=null;
        if(ak.contains("vInsurance/save")||ak.contains("vInsurance/update")){

                        if(StrUtil.isBlank(vInsurance.getLicensePlate())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(vInsurance.getLicensePlate())) {
                                                   int len=StrUtil.totalLength(vInsurance.getLicensePlate());
                                                   if(len>20){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "牌照号长度不能大于20");
                                                        return;
                                                   }
                                             }
                    if(vInsurance.getBAt()==null){
                            addError(Consts.REQ_JSON_CODE.fail.name(), "保险开始日期值不能为空");
                        return;
                    }

                    if(vInsurance.getEAt()==null){
                            addError(Consts.REQ_JSON_CODE.fail.name(), "保险结束日期值不能为空");
                        return;
                    }




                        if(StrUtil.isBlank(vInsurance.getNum())){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "保险单号值不能为空");
                        return;
                    }

                                             if (StrUtil.isNotBlank(vInsurance.getNum())) {
                                                   int len=StrUtil.totalLength(vInsurance.getNum());
                                                   if(len>50){
                                                        addError(Consts.REQ_JSON_CODE.fail.name(), "保险单号长度不能大于50");
                                                        return;
                                                   }
                                             }
                        if(vInsurance.getPrice()==null){
                        addError(Consts.REQ_JSON_CODE.fail.name(), "保单金额值不能为空");
                        return;
                    }


        }else if(ak.contains("vInsurance/del")||ak.contains("vInsurance/logicDel")){
            String ids =controller.getPara("ids");
            if(StrUtil.isBlank(ids)){
                addError(Consts.REQ_JSON_CODE.fail.name(), "缺少删除数据的关键数据");
                return;
            }
        }
        if(ak.contains("vInsurance/save")){
                       list=VInsurance.dao.findByPropEQWithDat("num",vInsurance.getNum());
                       if (!list.isEmpty()) {
                             addError(Consts.REQ_JSON_CODE.fail.name(), "保险单号值重复");
                             return;
                       }
        }else if(ak.contains("vInsurance/update")){

                                   list=VInsurance.dao.findByPropEQAndIdNEQWithDat("num",vInsurance.getNum(),vInsurance.getId());
                                   if (!list.isEmpty()) {
                                         addError(Consts.REQ_JSON_CODE.fail.name(), "保险单号值重复");
                                         return;
                                   }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }

}
