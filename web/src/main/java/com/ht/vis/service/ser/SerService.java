package com.ht.vis.service.ser;

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
import com.ht.vis.core.CoreData;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.Role;
import com.ht.vis.model.Ser;
import com.ht.vis.query.SerQuery;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SerService extends CoreService {

    private static Ser serDao = Ser.dao;

    public List<Ser> findAll(SerQuery serQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(serQuery.getCode())) {
            kv.put("code like", "%" + serQuery.getCode() + "%");
        }
        if (StrUtil.isNotBlank(serQuery.getName())) {
            kv.put("name like", "%" + serQuery.getName() + "%");
        }
        if(serQuery.getpId()!=null){
            kv.put("pId=",  serQuery.getpId() );
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(serQuery.getOrderBy())) {
            kv.put("orderBy", serQuery.getOrderBy());
        }
        kv = Ser.buildParamMap(Ser.class, kv);
        return serDao.findByAndCond(kv);
    }


    public Page<Ser> findPage(SerQuery serQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(serQuery.getCode())) {
            kv.put("code like", "%" + serQuery.getCode() + "%");
        }
        if (StrUtil.isNotBlank(serQuery.getName())) {
            kv.put("name like", "%" + serQuery.getName() + "%");
        }
        if(serQuery.getpId()!=null){
            kv.put("pId=",  serQuery.getpId() );
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(serQuery.getOrderBy())) {
            kv.put("orderBy", serQuery.getOrderBy());
        }
        kv = Ser.buildParamMap(Ser.class, kv);
        return serDao.pageByAndCond(kv, serQuery.getPn(), serQuery.getPs());
    }

    public Ser findOne(Integer id) {
        return serDao.findById(id);
    }

    @Before({Tx.class})
    public void save(Ser ser) {
        ser.save();
        CoreData.loadAllSer();//刷新全部服务的缓存
    }

    @Before({Tx.class})
    public void update(Ser ser) {
        ser.update();
        CoreData.loadAllSer();//刷新全部服务的缓存
        CacheKit.removeAll(Consts.CACHE_NAMES.userSers.name());
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        Ser ser = findOne(id);

        if (opId != null) {
            ser.setOpId(opId);
        }

        ser.apDel();
        CoreData.loadAllSer();//刷新全部服务的缓存
        CacheKit.removeAll(Consts.CACHE_NAMES.userSers.name());
    }

    @Before({Tx.class})
    public void del(Integer id) {
        Ser ser = findOne(id);
        ser.delete();
        CoreData.loadAllSer();//刷新全部服务的缓存
        CacheKit.removeAll(Consts.CACHE_NAMES.userSers.name());
    }

    @Before({Tx.class})
    public void batchLogicDel(Integer[] ids, Integer opId) {
        if (ids != null) {
            for (Integer id : ids) {
                logicDel(id, opId);
            }
        }
        CoreData.loadAllSer();//刷新全部服务的缓存
        CacheKit.removeAll(Consts.CACHE_NAMES.userSers.name());
    }

    @Before({Tx.class})
    public void batchDel(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                del(id);
            }
        }
        CoreData.loadAllSer();//刷新全部服务的缓存
        CacheKit.removeAll(Consts.CACHE_NAMES.userSers.name());
    }

    public List<Ser> findSersByRoleCodesInCache(String[] roleCodes){
        String str=ArrayUtil.join(roleCodes,",");
        Kv kv=Kv.by("cond",roleCodes);
        SqlPara sqlPara= Db.getSqlPara("serSql.findByCodes",kv);
        return Ser.dao.findByCache(Consts.CACHE_NAMES.userSers.name(),"findSersByRoleCodesInCache_"+str,sqlPara.getSql());
    }

    public Set<String> findUrlByRoleCodesInCache(String[] roleCodes){
        String s= ArrayUtil.join(roleCodes,",");
        Object o= CacheKit.get(Consts.CACHE_NAMES.userSers.name(),"findUrlByRoleCodesInCache_"+s);
        if(o==null) {
            Set<String> ret = new HashSet<>();
            List<Ser> serList = findSersByRoleCodesInCache(roleCodes);
            for (Ser ser : serList) {
                ret.add(ser.getUrl());
            }
            if(!ret.isEmpty())CacheKit.put(Consts.CACHE_NAMES.userSers.name(),"findUrlByRoleCodesInCache_"+s,ret);
            return ret;
        }else{
            return (Set<String>)o;
        }
    }
}
