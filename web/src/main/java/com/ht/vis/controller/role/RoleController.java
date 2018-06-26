package com.ht.vis.controller.role;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.ht.vis.core.CoreController;
import com.ht.vis.interceptors.AdminAAuthInterceptor;
import com.ht.vis.model.Res;
import com.ht.vis.model.Role;
import com.ht.vis.model.Ser;
import com.ht.vis.query.RoleQuery;
import com.ht.vis.service.role.RoleService;
import com.ht.vis.validator.role.RoleValidator;

import java.util.List;


public class RoleController extends CoreController {

    private RoleService roleService = Duang.duang(RoleService.class.getSimpleName(), RoleService.class);

    public void list() {
        RoleQuery roleQuery = (RoleQuery) getQueryModel(RoleQuery.class);
        List<Role> ret = roleService.findAll(roleQuery);
        renderJson(ret);
    }

    public void page() {
        RoleQuery roleQuery = (RoleQuery) getQueryModel(RoleQuery.class);
        Page<Role> ret = roleService.findPage(roleQuery);
        renderJson(ret);
    }

    @Before({RoleValidator.class})
    public void save() {
        Role role = getApModel(Role.class);
        if (currUser() != null) {
            role.setOpId(currUser().getId());
        }
        roleService.save(role);
        renderSuccessJSON("角色表新增成功");
    }

    @Before({RoleValidator.class})
    public void update() {
        Role role = getApModel(Role.class);
        if (currUser() != null) {
            role.setOpId(currUser().getId());
        }
        roleService.update(role);
        renderSuccessJSON("角色表修改成功");
    }

    //逻辑删除
    @Before({RoleValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        roleService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("角色表删除成功");
    }

    //真实删除
    @Before({RoleValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        roleService.batchDel(ids);
        renderSuccessJSON("角色表删除成功");
    }
    @Clear(AdminAAuthInterceptor.class)
    public void get() {
        Integer id = getParaToInt("id");
        Role role=roleService.findOne(id);
        List<Res> resList=roleService.findOwnResesByRoleCode(role.getCode());
        if(!resList.isEmpty())role.setOwnReses(resList);
        List<Ser> serList=roleService.findOwnSersByRoleCode(role.getCode());
        if(!serList.isEmpty())role.setOwnSers(serList);
        renderJson(JSON.toJSONString(role, SerializerFeature.DisableCircularReferenceDetect));
    }

    public void saveRoleReses(){
        String roleCode=getPara("roleCode");
        String reses=getPara("reses");
        if(StrUtil.isNotBlank(reses)&&StrUtil.isNotBlank(roleCode))
            roleService.saveRoleReses(roleCode,reses.split(","));
        else{
            renderFailJSON("缺少角色编号或者菜单编号");
            return;
        }
        renderSuccessJSON("角色菜单关系设置成功");

    }

    public void saveRoleSers(){
        String roleCode=getPara("roleCode");
        String sers=getPara("sers");
        if(StrUtil.isNotBlank(sers)&&StrUtil.isNotBlank(roleCode))
            roleService.saveRoleSers(roleCode,sers.split(","));
        else{
            renderFailJSON("缺少角色编号或者服务编号");
            return;
        }
        renderSuccessJSON("角色服务关系设置成功");

    }
}