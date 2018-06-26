package com.ht.vis.validator.dd;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreController;
import com.ht.vis.core.CoreValidator;
import com.ht.vis.model.Dd;

import java.util.List;

/**
 * 简介
 * <p>
 * 项目名称:   [ap]
 * 包:        [com.ht.vis.sm.dd]
 * 类名称:    [DdValidator]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/5/26]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class DdValidator extends CoreValidator {
    @Override
    protected void validate(Controller controller) {
        Dd dd=controller.getModel(Dd.class,"",true);
        String ak=getActionKey();
        List<Dd> list=null;
        if(StrUtil.isBlank(dd.getVal())&&dd.getPId()!=0){
            addError(Consts.REQ_JSON_CODE.fail.name(), "值不能为空");
            return;
        }
        if(StrUtil.isBlank(dd.getName())){
            addError(Consts.REQ_JSON_CODE.fail.name(), "名字不能为空");
            return;
        }
        if(StrUtil.isBlank(dd.getDict())){
            addError(Consts.REQ_JSON_CODE.fail.name(), "字典不能为空");
            return;
        }
        if(ak.contains("save")){
            if(StrUtil.isNotBlank(dd.getVal())) {
                list = Dd.dao.findByValAndPId(dd.getVal(),dd.getPId().intValue());
                if (!list.isEmpty()) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "值重复");
                    return;
                }
            }
            list=Dd.dao.findByNameAndPId(dd.getName(),dd.getPId().intValue());
            if(!list.isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(),"名字重复");return;
            }

        }else if(ak.contains("update")){
            if(StrUtil.isNotBlank(dd.getVal())) {
                list = Dd.dao.findByValAndDictAndNeId(dd);
                if (!list.isEmpty()) {
                    addError(Consts.REQ_JSON_CODE.fail.name(), "值重复");
                    return;
                }
            }
            list=Dd.dao.findByNameAndDictAndNeId(dd);
            if(!list.isEmpty()){
                addError(Consts.REQ_JSON_CODE.fail.name(),"名字重复");return;
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson(getErrorJSON());
    }
}