package com.ht.vis.service.res;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.Res;
import com.ht.vis.query.ResQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResService extends CoreService {

    private static Res resDao = Res.dao;

    public List<Res> findAll(ResQuery resQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(resQuery.getName())) {
            kv.put("name like", "%" + resQuery.getName() + "%");
        }
        if (StrUtil.isNotBlank(resQuery.getEnabled())) {
            kv.put("enabled=", resQuery.getEnabled());
        }
        if (resQuery.getPId()!=null) {
            kv.put("pId=", resQuery.getPId());
        }
        if (StrUtil.isNotBlank(resQuery.getCode())) {
            kv.put("code like", "%" + resQuery.getCode() + "%");
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(resQuery.getOrderBy())) {
            kv.put("orderBy", resQuery.getOrderBy());
        }
        kv = Res.buildParamMap(Res.class, kv);
        return resDao.findByAndCond(kv);
    }


    public Page<Res> findPage(ResQuery resQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(resQuery.getName())) {
            kv.put("name like", "%" + resQuery.getName() + "%");
        }
        if (StrUtil.isNotBlank(resQuery.getEnabled())) {
            kv.put("enabled=", resQuery.getEnabled());
        }
        if (resQuery.getPId()!=null) {
            kv.put("pId=", resQuery.getPId());
        }
        if (StrUtil.isNotBlank(resQuery.getCode())) {
            kv.put("code like", "%" + resQuery.getCode() + "%");
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(resQuery.getOrderBy())) {
            kv.put("orderBy", resQuery.getOrderBy());
        }
        kv = Res.buildParamMap(Res.class, kv);
        return resDao.pageByAndCond(kv, resQuery.getPn(), resQuery.getPs());
    }

    public Res findOne(Integer id) {
        return resDao.findById(id);
    }

    @Before({Tx.class})
    public void save(Res res) {
        res.save();
        CacheKit.removeAll(Consts.CACHE_NAMES.userReses.name());
    }

    @Before({Tx.class})
    public void update(Res res) {
        res.update();
        CacheKit.removeAll(Consts.CACHE_NAMES.userReses.name());
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        Res res = findOne(id);

        if (opId != null) {
            res.setOpId(opId);
        }

        res.apDel();
        CacheKit.removeAll(Consts.CACHE_NAMES.userReses.name());
    }

    @Before({Tx.class})
    public void del(Integer id) {
        Res res = findOne(id);
        res.delete();
        CacheKit.removeAll(Consts.CACHE_NAMES.userReses.name());
    }

    @Before({Tx.class})
    public void batchLogicDel(Integer[] ids, Integer opId) {
        if (ids != null) {
            for (Integer id : ids) {
                logicDel(id, opId);
            }
        }
        CacheKit.removeAll(Consts.CACHE_NAMES.userReses.name());
    }

    @Before({Tx.class})
    public void batchDel(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                del(id);
            }
        }
        CacheKit.removeAll(Consts.CACHE_NAMES.userReses.name());
    }

    public List<Res> findByRoleCodesInCache(String[] roleCodes){
        String s= ArrayUtil.join(roleCodes,",");
        Kv kv=Kv.by("cond",roleCodes);
        SqlPara sqlPara=Db.getSqlPara("resSql.findByCodes",kv);
        return Res.dao.findByCache(Consts.CACHE_NAMES.userReses.name(),"findByRoleCodesInCache_"+s,sqlPara.getSql());
    }

    public Set<String> findUrlByRoleCodesInCache(String[] roleCodes){
        String s= ArrayUtil.join(roleCodes,",");
        Object o= CacheKit.get(Consts.CACHE_NAMES.userReses.name(),"findUrlByRoleCodesInCache_"+s);
        if(o==null) {

            Set<String> ret = new HashSet<>();
            List<Res> resList = findByRoleCodesInCache(roleCodes);
            for (Res res : resList) {
                ret.add(res.getUrl());
            }
            if(!ret.isEmpty())CacheKit.put(Consts.CACHE_NAMES.userReses.name(),"findUrlByRoleCodesInCache_"+s,ret);

            return ret;
        }else{
            return (Set<String>)o;
        }
    }




}
