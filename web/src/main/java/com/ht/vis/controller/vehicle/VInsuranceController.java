package com.ht.vis.controller.vehicle;

import com.ht.vis.core.CoreController;
import com.ht.vis.model.VInsurance;
import com.ht.vis.query.VInsuranceQuery;
import com.ht.vis.service.vehicle.VInfoService;
import com.ht.vis.service.vehicle.VInsuranceService;
import com.ht.vis.validator.vehicle.VInsuranceValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VInsuranceController extends CoreController{

    private VInsuranceService vInsuranceService=Duang.duang(VInsuranceService.class.getSimpleName(),VInsuranceService.class);
    private VInfoService vInfoService=Duang.duang(VInfoService.class.getSimpleName(),VInfoService.class);

    public void list(){
        VInsuranceQuery vInsuranceQuery=(VInsuranceQuery)getQueryModel(VInsuranceQuery.class);
        List<VInsurance> ret=vInsuranceService.findAll(vInsuranceQuery);
        renderJson(ret);
    }

    public void page(){
        VInsuranceQuery vInsuranceQuery=(VInsuranceQuery)getQueryModel(VInsuranceQuery.class);
        Page<VInsurance> ret=vInsuranceService.findPage(vInsuranceQuery);
        renderJson(ret);
    }
    @Before({VInsuranceValidator.class})
    public void save(){
        VInsurance vInsurance=getApModel(VInsurance.class);
                if(currUser()!=null){vInsurance.setOpId(currUser().getId());}
        vInsuranceService.save(vInsurance);
        renderSuccessJSON("车辆保险新增成功");
    }
    @Before({VInsuranceValidator.class})
    public void update(){
        VInsurance vInsurance=getApModel(VInsurance.class);
                        if(currUser()!=null){vInsurance.setOpId(currUser().getId());}
        vInsuranceService.update(vInsurance);
        renderSuccessJSON("车辆保险修改成功");
    }

    //逻辑删除
    @Before({VInsuranceValidator.class})
    public void logicDel(){
        Integer[] ids=getParaValuesToInt("ids");
        vInsuranceService.batchLogicDel(ids,currUser()==null?null:currUser().getId());
        renderSuccessJSON("车辆保险删除成功");
    }

    //真实删除
    @Before({VInsuranceValidator.class})
    public void del(){
        Integer[] ids=getParaValuesToInt("ids");
        vInsuranceService.batchDel(ids);
        renderSuccessJSON("车辆保险删除成功");
    }
    public void get(){
        Integer id=getParaToInt("id");
        renderJson(vInsuranceService.findOne(id));
    }

    public void init(){
        Map<String,Object> ret=new HashMap<>();
        ret.put(VInfoService.CACHEKEY_ALLVINFO,vInfoService.findAllInCache());
        renderJson(ret);
    }

}