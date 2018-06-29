package com.ht.vis.service.company;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.CInfo;
import com.ht.vis.query.CInfoQuery;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;
import java.util.*;
import com.jfinal.plugin.activerecord.Page;

public class CInfoService extends CoreService{

    private static CInfo cInfoDao=CInfo.dao;

    public List<CInfo> findAll(CInfoQuery cInfoQuery){
         Kv kv= Kv.create();
         kv.put("dAt","");
         if(StrUtil.isNotBlank(cInfoQuery.getOrderBy())) {
             kv.put("orderBy", cInfoQuery.getOrderBy());
         }
         kv=CInfo.buildParamMap(CInfo.class,kv);
         return cInfoDao.findByAndCond(kv);
    }


    public Page<CInfo> findPage(CInfoQuery cInfoQuery){
        Kv kv= Kv.create();
             kv.put("dAt","");
             if(StrUtil.isNotBlank(cInfoQuery.getOrderBy())) {
                kv.put("orderBy", cInfoQuery.getOrderBy());
             }
             kv=CInfo.buildParamMap(CInfo.class,kv);
             return cInfoDao.pageByAndCond(kv,cInfoQuery.getPn(),cInfoQuery.getPs());
    }

    public CInfo findOne(Integer id){
           return cInfoDao.findById(id);
    }
    @Before({Tx.class})
    public void save(CInfo cInfo){
        cInfo.save();
    }
    @Before({Tx.class})
    public void update(CInfo cInfo){
            cInfo.update();
    }
    @Before({Tx.class})
    public void logicDel(Integer id,Integer opId){
            CInfo cInfo=findOne(id);

                if(opId!=null){
                    cInfo.setOpId(opId);
                }

            cInfo.apDel();

    }
    @Before({Tx.class})
    public void del(Integer id){
            CInfo cInfo=findOne(id);
            cInfo.delete();
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

