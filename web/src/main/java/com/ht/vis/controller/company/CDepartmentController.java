package com.ht.vis.controller.company;

import com.ht.vis.core.CoreController;
import com.ht.vis.model.CDepartment;
import com.ht.vis.query.CDepartmentQuery;
import com.ht.vis.service.company.CDepartmentService;
import com.ht.vis.validator.company.CDepartmentValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CDepartmentController extends CoreController{

    private CDepartmentService cDepartmentService=Duang.duang(CDepartmentService.class.getSimpleName(),CDepartmentService.class);

    public void list(){
        CDepartmentQuery cDepartmentQuery=(CDepartmentQuery)getQueryModel(CDepartmentQuery.class);
        List<CDepartment> ret=cDepartmentService.findAll(cDepartmentQuery);
        renderJson(ret);
    }

    public void page(){
        CDepartmentQuery cDepartmentQuery=(CDepartmentQuery)getQueryModel(CDepartmentQuery.class);
        Page<CDepartment> ret=cDepartmentService.findPage(cDepartmentQuery);
        renderJson(ret);
    }
    @Before({CDepartmentValidator.class})
    public void save(){
        CDepartment cDepartment=getApModel(CDepartment.class);
        cDepartmentService.save(cDepartment);
        renderSuccessJSON("部门新增成功");
    }
    @Before({CDepartmentValidator.class})
    public void update(){
        CDepartment cDepartment=getApModel(CDepartment.class);
        cDepartmentService.update(cDepartment);
        renderSuccessJSON("部门修改成功");
    }

    //逻辑删除
    @Before({CDepartmentValidator.class})
    public void logicDel(){
        Integer[] ids=getParaValuesToInt("ids");
        cDepartmentService.batchLogicDel(ids,currUser()==null?null:currUser().getId());
        renderSuccessJSON("部门删除成功");
    }

    //真实删除
    @Before({CDepartmentValidator.class})
    public void del(){
        Integer[] ids=getParaValuesToInt("ids");
        cDepartmentService.batchDel(ids);
        renderSuccessJSON("部门删除成功");
    }
    public void get(){
        Integer id=getParaToInt("id");
        renderJson(cDepartmentService.findOne(id));
    }
    public void init(){
           Map<String ,Object> ret=new HashMap<>();

           renderJson(ret);
    }
}