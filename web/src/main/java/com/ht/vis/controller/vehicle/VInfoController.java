package com.ht.vis.controller.vehicle;

import com.ht.vis.core.CoreController;
import com.ht.vis.model.VInfo;
import com.ht.vis.query.VInfoQuery;
import com.ht.vis.service.vehicle.VInfoService;
import com.ht.vis.validator.vehicle.VInfoValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;


public class VInfoController extends CoreController{

    private VInfoService vInfoService=Duang.duang(VInfoService.class.getSimpleName(),VInfoService.class);

    public void list(){
        VInfoQuery vInfoQuery=(VInfoQuery)getQueryModel(VInfoQuery.class);
        List<VInfo> ret=vInfoService.findAll(vInfoQuery);
        renderJson(ret);
    }

    public void page(){
        VInfoQuery vInfoQuery=(VInfoQuery)getQueryModel(VInfoQuery.class);
        Page<VInfo> ret=vInfoService.findPage(vInfoQuery);
        renderJson(ret);
    }
    @Before({VInfoValidator.class})
    public void save(){
        VInfo vInfo=getApModel(VInfo.class);
                if(currUser()!=null){vInfo.setOpId(currUser().getId());}
        vInfoService.save(vInfo);
        renderSuccessJSON("车辆信息新增成功");
    }
    @Before({VInfoValidator.class})
    public void update(){
        VInfo vInfo=getApModel(VInfo.class);
                        if(currUser()!=null){vInfo.setOpId(currUser().getId());}
        vInfoService.update(vInfo);
        renderSuccessJSON("车辆信息修改成功");
    }

    //逻辑删除
    @Before({VInfoValidator.class})
    public void logicDel(){
        Integer[] ids=getParaValuesToInt("ids");
        vInfoService.batchLogicDel(ids,currUser()==null?null:currUser().getId());
        renderSuccessJSON("车辆信息删除成功");
    }

    //真实删除
    @Before({VInfoValidator.class})
    public void del(){
        Integer[] ids=getParaValuesToInt("ids");
        vInfoService.batchDel(ids);
        renderSuccessJSON("车辆信息删除成功");
    }
    public void get(){
        Integer id=getParaToInt("id");
        renderJson(vInfoService.findOne(id));
    }
}