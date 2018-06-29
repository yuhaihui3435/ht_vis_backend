package com.ht.vis.controller.dd;

import com.ht.vis.core.CoreData;
import com.jfinal.aop.Before;
import com.ht.vis.core.CoreController;
import com.ht.vis.model.Dd;
import com.ht.vis.validator.dd.DdValidator;


public class DdCtr extends CoreController {

    public void list(){
        renderJson(Dd.dao.findParentAll());
    }
    @Before({DdValidator.class})
    public void save(){
        Dd dd=getApModel(Dd.class);
        if(currUser()!=null)dd.setOpId(currUser().getId());
        dd.save();
        CoreData.loadDd();
        renderSuccessJSON("数据字典新增成功");
    }
    @Before({DdValidator.class})
    public void update(){
        Dd dd=getApModel(Dd.class);
        if(currUser()!=null)dd.setOpId(currUser().getId());
        dd.update();
        CoreData.loadDd();
        renderSuccessJSON("数据字典修改成功");
    }

    public void del(){
        int id=getParaToInt("id");
        Dd dd=Dd.dao.findById(id);
        dd.apDel();
        CoreData.loadDd();
        renderSuccessJSON("数据字典删除成功");
    }

    public void get(){
        int id=getParaToInt("id");
        renderJson(Dd.dao.findById(id));
    }
    public void getByModule(){
        String module=getPara("module");
        renderJson(Dd.dao.findByDict(module));
    }
}