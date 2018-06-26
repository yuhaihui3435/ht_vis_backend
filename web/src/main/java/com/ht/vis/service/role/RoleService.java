package com.ht.vis.service.role;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.mysql.jdbc.ResultSetRow;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.*;
import com.ht.vis.query.RoleQuery;

import java.util.*;

public class RoleService extends CoreService {

    private static Role roleDao = Role.dao;

    public List<Role> findAll(RoleQuery roleQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(roleQuery.getName())) {
            kv.put("name like", "%" + roleQuery.getName() + "%");
        }
        if (StrUtil.isNotBlank(roleQuery.getCode())) {
            kv.put("code like", "%" + roleQuery.getCode() + "%");
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(roleQuery.getOrderBy())) {
            kv.put("orderBy", roleQuery.getOrderBy());
        }
        kv = Role.buildParamMap(Role.class, kv);
        return roleDao.findByAndCond(kv);
    }


    public Page<Role> findPage(RoleQuery roleQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(roleQuery.getName())) {
            kv.put("name like", "%" + roleQuery.getName() + "%");
        }
        if (StrUtil.isNotBlank(roleQuery.getCode())) {
            kv.put("code like", "%" + roleQuery.getCode() + "%");
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(roleQuery.getOrderBy())) {
            kv.put("orderBy", roleQuery.getOrderBy());
        }
        kv = Role.buildParamMap(Role.class, kv);
        return roleDao.pageByAndCond(kv, roleQuery.getPn(), roleQuery.getPs());
    }

    public Role findOne(Integer id) {
        return roleDao.findById(id);
    }

    @Before({Tx.class})
    public void save(Role role) {
        role.save();
        CacheKit.removeAll(Consts.CACHE_NAMES.userRoles.name());
    }

    @Before({Tx.class})
    public void update(Role role) {
        role.update();
        CacheKit.removeAll(Consts.CACHE_NAMES.userRoles.name());

    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        Role role = findOne(id);

        if (opId != null) {
            role.setOpId(opId);
        }

        role.apDel();
        CacheKit.removeAll(Consts.CACHE_NAMES.userRoles.name());
    }

    @Before({Tx.class})
    public void del(Integer id) {
        Role role = findOne(id);
        role.delete();
        CacheKit.removeAll(Consts.CACHE_NAMES.userRoles.name());
    }

    @Before({Tx.class})
    public void batchLogicDel(Integer[] ids, Integer opId) {
        if (ids != null) {
            for (Integer id : ids) {
                logicDel(id, opId);
            }
        }
        CacheKit.removeAll(Consts.CACHE_NAMES.userRoles.name());
    }

    @Before({Tx.class})
    public void batchDel(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                del(id);
            }
        }
        CacheKit.removeAll(Consts.CACHE_NAMES.userRoles.name());
    }

    /**
     *
     * 保存角色菜单关系
     *
     * @param roleCode
     * @param reses
     */
    @Before({Tx.class})
    public void saveRoleReses(String roleCode,String[] reses){
        RoleRes.dao.delByRoleCode(roleCode);
        RoleRes roleRes=null;
        Set<String> resSet=new HashSet<>();
        List<RoleRes> roleResList=new ArrayList<>();
        for (String res:reses){
            dealRoleResParentAndChildren(resSet,res);
        }
        Iterator<String> it=resSet.iterator();
        while(it.hasNext()){
            String resCode=it.next();
            roleRes =new RoleRes();
            roleRes.setResCode(resCode);
            roleRes.setRoleCode(roleCode);
            roleResList.add(roleRes);
        }
        Db.batchSave(roleResList,15);
        CacheKit.removeAll(Consts.CACHE_NAMES.userReses.name());
    }

    /**
     * 递归获取菜单孩子节点，获取父亲节点
     * @param resCodes
     * @param resCode
     */
    private void dealRoleResParentAndChildren(Set<String> resCodes,String resCode){
        List<Res> resList=Res.dao.findByPropEQWithDat("code",resCode);
        List<Res> _children=null;
        if(!resList.isEmpty()) {
            Res res = resList.get(0);
//            if(res.getPId()>0) {
//                Res pRes = Res.dao.findById(res.getPId());
//                resCodes.add(pRes.getCode());
//            }
            _children=res.getChildren();
            if(_children!=null) {
                for (Res r : _children) {
                    resCodes.add(r.getCode());
                    dealRoleResParentAndChildren(resCodes, r.getCode());
                }
            }else{
                resCodes.add(resCode);
            }
        }
    }

    /**
     *
     * 保存角色服务关系
     *
     * @param roleCode
     * @param sers
     */
    @Before({Tx.class})
    public void saveRoleSers(String roleCode,String[] sers){
        RoleSer.dao.delByRoleCode(roleCode);
        RoleSer roleSer=null;
        Set<String> serSet=new HashSet<>();
        List<RoleSer> roleSerList=new ArrayList<>();
        for (String ser:sers){
            dealRoleSerParentAndChildren(serSet,ser);
        }
        Iterator<String> it=serSet.iterator();
        while(it.hasNext()){
            String serCode=it.next();
            roleSer =new RoleSer();
            roleSer.setRoleCode(roleCode);
            roleSer.setSerCode(serCode);
            roleSerList.add(roleSer);
        }
        Db.batchSave(roleSerList,15);
        CacheKit.removeAll(Consts.CACHE_NAMES.userSers.name());
    }

    /**
     * 递归获取服务孩子节点，获取父亲节点
     * @param serCodes
     * @param serCode
     */
    private void dealRoleSerParentAndChildren(Set<String> serCodes,String serCode){
        List<Ser> serList= Ser.dao.findByPropEQWithDat("code",serCode);
        List<Ser> _children=null;
        if(!serList.isEmpty()) {
            Ser ser = serList.get(0);
            _children=ser.getChildren();
            if(_children!=null) {
                for (Ser r : _children) {
                    if(r.getChildren()==null||r.getChildren().isEmpty()){
                        serCodes.add(r.getCode());
                    }
                    dealRoleSerParentAndChildren(serCodes, r.getCode());
                }
            }else{
                serCodes.add(serCode);
            }
        }
    }

    public List<Res> findOwnResesByRoleCode(String roleCode){
        String sql="select res.* from  s_role_res rr  left join s_role r on r.code=rr.roleCode left join s_res res on rr.resCode=res.code" +
                " where r.code=? and r.dAt is null and res.dAt is null ";
        return Res.dao.find(sql,roleCode);
    }

    public List<Ser> findOwnSersByRoleCode(String roleCode){
        String sql="select ser.* from  s_role_ser rs  left join  s_role r on r.code=rs.roleCode left join s_ser ser on rs.serCode=ser.code" +
                " where r.code=? and r.dAt is null and ser.dAt is null ";
        return Ser.dao.find(sql,roleCode);
    }

    public List<Role> findByLoginnameInCache(String loginname){
        return Role.dao.findByLoginnameInCache(loginname);
    }

    public String[] findCodesByLoginnameInCache(String loginname){

        Object o=CacheKit.get(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+loginname);
        if(o==null) {
            List<Role> roleList = findByLoginnameInCache(loginname);
            String[] roleCodes = new String[roleList.size()];
            for (int i = 0; i < roleList.size(); i++) {
                roleCodes[i] = roleList.get(i).getCode();
            }
            CacheKit.put(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+loginname,roleCodes);
            return roleCodes;
        }else{
            return (String[])o;
        }
    }
}
