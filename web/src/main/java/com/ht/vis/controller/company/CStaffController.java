package com.ht.vis.controller.company;

import com.ht.vis.Consts;
import com.ht.vis.core.CoreController;
import com.ht.vis.model.CDepartment;
import com.ht.vis.model.CDepartmentStaff;
import com.ht.vis.model.CStaff;
import com.ht.vis.query.CDepartmentQuery;
import com.ht.vis.query.CStaffQuery;
import com.ht.vis.service.company.CDepartmentService;
import com.ht.vis.service.company.CStaffService;
import com.ht.vis.validator.company.CStaffValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CStaffController extends CoreController{

    private CDepartmentService cDepartmentService=Duang.duang(CDepartmentService.class.getSimpleName(),CDepartmentService.class);
    private CStaffService cStaffService=Duang.duang(CStaffService.class.getSimpleName(),CStaffService.class);

    public void list(){
        CStaffQuery cStaffQuery=(CStaffQuery)getQueryModel(CStaffQuery.class);
        List<CStaff> ret=cStaffService.findAll(cStaffQuery);
        renderJson(ret);
    }

    public void page(){
        CStaffQuery cStaffQuery=(CStaffQuery)getQueryModel(CStaffQuery.class);
        Page<CStaff> ret=cStaffService.findPage(cStaffQuery);
        renderJson(ret);
    }
    @Before({CStaffValidator.class})
    public void save(){
        CStaff cStaff=getApModel(CStaff.class);
        cStaff.setCCode(currUser().getCCode());
        cStaffService.save(cStaff);
        renderSuccessJSON("员工新增成功");
    }
    @Before({CStaffValidator.class})
    public void update(){
        CStaff cStaff=getApModel(CStaff.class);
        cStaffService.update(cStaff);
        renderSuccessJSON("员工修改成功");
    }

    //逻辑删除
    @Before({CStaffValidator.class})
    public void logicDel(){
        Integer[] ids=getParaValuesToInt("ids");
        cStaffService.batchLogicDel(ids,currUser()==null?null:currUser().getId());
        renderSuccessJSON("员工删除成功");
    }

    //真实删除
    @Before({CStaffValidator.class})
    public void del(){
        Integer[] ids=getParaValuesToInt("ids");
        cStaffService.batchDel(ids);
        renderSuccessJSON("员工删除成功");
    }
    public void get(){
        Integer id=getParaToInt("id");
        renderJson(cStaffService.findOne(id));
    }
    public void init(){
           Map<String ,Object> ret=new HashMap<>();

           renderJson(ret);
    }
    //查询员工部门职位数据
    public void listDj(){
        Map<String,Object> ret=new HashMap<>();
        String staffCode=getPara("staffCode");
        List<CDepartment> cDepartments=cDepartmentService.findAll(new CDepartmentQuery());
        ret.put("departmentList",cDepartments);
        List<CDepartmentStaff> cDepartmentStaffs=cStaffService.findDJbyCode(staffCode);
        ret.put("djList",cDepartmentStaffs);
        ret.put("jobList", CacheKit.get(Consts.CACHE_NAMES.dd.name(),"jobList"));
        renderJson(ret);
    }

    //保存员工部门职位数据
    @Before({CStaffValidator.class})
    public void saveDj(){
        CDepartmentStaff cDepartmentStaff=getApModel(CDepartmentStaff.class);
        cDepartmentStaff.save();
        renderSuccessJSON("部门职位设置成功");
    }

    public void delDj(){
        Integer id=getParaToInt("djId");
        CDepartmentStaff.dao.deleteById(id);
        renderSuccessJSON("部门职位设置删除成功");
    }
}