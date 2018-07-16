package com.ht.vis.service;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.SFileStorage;
import com.ht.vis.query.SFileStorageQuery;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;
import java.util.*;
import com.jfinal.plugin.activerecord.Page;

public class SFileStorageService extends CoreService{

    private static SFileStorage sFileStorageDao=SFileStorage.dao;

    public List<SFileStorage> findAll(SFileStorageQuery sFileStorageQuery){
         Kv kv= Kv.create();
                    if(StrUtil.isNotBlank(sFileStorageQuery.getTarget())){
                         kv.put("target=",sFileStorageQuery.getTarget());
                    }
                    if(StrUtil.isNotBlank(sFileStorageQuery.getType())){
                         kv.put("type=",sFileStorageQuery.getType());
                    }
                    if(StrUtil.isNotBlank(sFileStorageQuery.getName())){
                         kv.put("name=",sFileStorageQuery.getName());
                    }
                    if(StrUtil.isNotBlank(sFileStorageQuery.getCode())){
                         kv.put("code=",sFileStorageQuery.getCode());
                    }
         kv.put("dAt","");
         if(StrUtil.isNotBlank(sFileStorageQuery.getOrderBy())) {
             kv.put("orderBy", sFileStorageQuery.getOrderBy());
         }
         kv=SFileStorage.buildParamMap(SFileStorage.class,kv);
         return sFileStorageDao.findByAndCond(kv);
    }


    public Page<SFileStorage> findPage(SFileStorageQuery sFileStorageQuery){
        Kv kv= Kv.create();
                        if(StrUtil.isNotBlank(sFileStorageQuery.getTarget())){
                             kv.put("target=",sFileStorageQuery.getTarget());
                        }
                        if(StrUtil.isNotBlank(sFileStorageQuery.getType())){
                             kv.put("type=",sFileStorageQuery.getType());
                        }
                        if(StrUtil.isNotBlank(sFileStorageQuery.getName())){
                             kv.put("name=",sFileStorageQuery.getName());
                        }
                        if(StrUtil.isNotBlank(sFileStorageQuery.getCode())){
                             kv.put("code=",sFileStorageQuery.getCode());
                        }
             kv.put("dAt","");
             if(StrUtil.isNotBlank(sFileStorageQuery.getOrderBy())) {
                kv.put("orderBy", sFileStorageQuery.getOrderBy());
             }
             kv=SFileStorage.buildParamMap(SFileStorage.class,kv);
             return sFileStorageDao.pageByAndCond(kv,sFileStorageQuery.getPn(),sFileStorageQuery.getPs());
    }

    public SFileStorage findOne(Integer id){
           return sFileStorageDao.findById(id);
    }
    @Before({Tx.class})
    public void save(SFileStorage sFileStorage){
        sFileStorage.save();
    }
    @Before({Tx.class})
    public void update(SFileStorage sFileStorage){
            sFileStorage.update();
    }
    @Before({Tx.class})
    public void logicDel(Integer id,Integer opId){
            SFileStorage sFileStorage=findOne(id);


            sFileStorage.apDel();

    }
    @Before({Tx.class})
    public void del(Integer id){
            SFileStorage sFileStorage=findOne(id);
            sFileStorage.delete();
    }
    @Before({Tx.class})
    public void batchLogicDel(Integer[] ids,Integer opId){
            if(ids!=null){
                for(Integer id:ids){
                    logicDel(id,opId);
                }
            }
    }
    @Before({Tx.class})
    public void batchDel(Integer[] ids){
           if(ids!=null){
                for(Integer id:ids){
                    del(id);
                }
           }
    }
}

