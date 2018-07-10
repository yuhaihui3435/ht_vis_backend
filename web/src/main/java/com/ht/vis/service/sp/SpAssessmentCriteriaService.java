package com.ht.vis.service.sp;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.SpAssessmentCriteria;
import com.ht.vis.query.SpAssessmentCriteriaQuery;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;
import java.util.*;
import com.jfinal.plugin.activerecord.Page;

public class SpAssessmentCriteriaService extends CoreService{

    private static SpAssessmentCriteria spAssessmentCriteriaDao=SpAssessmentCriteria.dao;

    public List<SpAssessmentCriteria> findAll(SpAssessmentCriteriaQuery spAssessmentCriteriaQuery){
         Kv kv= Kv.create();
                    if(spAssessmentCriteriaQuery.getNorm()!=null){
                        kv.put("norm=",spAssessmentCriteriaQuery.getNorm());
                    }
                    if(spAssessmentCriteriaQuery.getTitle()!=null){
                        kv.put("title=",spAssessmentCriteriaQuery.getTitle());
                    }
                    if(StrUtil.isNotBlank(spAssessmentCriteriaQuery.getStars())){
                         kv.put("stars=",spAssessmentCriteriaQuery.getStars());
                    }
         kv.put("dAt","");
         if(StrUtil.isNotBlank(spAssessmentCriteriaQuery.getOrderBy())) {
             kv.put("orderBy", spAssessmentCriteriaQuery.getOrderBy());
         }
         kv=SpAssessmentCriteria.buildParamMap(SpAssessmentCriteria.class,kv);
         return spAssessmentCriteriaDao.findByAndCond(kv);
    }


    public Page<SpAssessmentCriteria> findPage(SpAssessmentCriteriaQuery spAssessmentCriteriaQuery){
        Kv kv= Kv.create();
                        if(spAssessmentCriteriaQuery.getNorm()!=null){
                            kv.put("norm=",spAssessmentCriteriaQuery.getNorm());
                        }
                        if(spAssessmentCriteriaQuery.getTitle()!=null){
                            kv.put("title=",spAssessmentCriteriaQuery.getTitle());
                        }
                        if(StrUtil.isNotBlank(spAssessmentCriteriaQuery.getStars())){
                             kv.put("stars=",spAssessmentCriteriaQuery.getStars());
                        }
             kv.put("dAt","");
             if(StrUtil.isNotBlank(spAssessmentCriteriaQuery.getOrderBy())) {
                kv.put("orderBy", spAssessmentCriteriaQuery.getOrderBy());
             }
             kv=SpAssessmentCriteria.buildParamMap(SpAssessmentCriteria.class,kv);
             return spAssessmentCriteriaDao.pageByAndCond(kv,spAssessmentCriteriaQuery.getPn(),spAssessmentCriteriaQuery.getPs());
    }

    public SpAssessmentCriteria findOne(Integer id){
           return spAssessmentCriteriaDao.findById(id);
    }
    @Before({Tx.class})
    public void save(SpAssessmentCriteria spAssessmentCriteria){
        spAssessmentCriteria.save();
    }
    @Before({Tx.class})
    public void update(SpAssessmentCriteria spAssessmentCriteria){
            spAssessmentCriteria.update();
    }
    @Before({Tx.class})
    public void logicDel(Integer id,Integer opId){
            SpAssessmentCriteria spAssessmentCriteria=findOne(id);

                if(opId!=null){
                    spAssessmentCriteria.setOpId(opId);
                }

            spAssessmentCriteria.apDel();

    }
    @Before({Tx.class})
    public void del(Integer id){
            SpAssessmentCriteria spAssessmentCriteria=findOne(id);
            spAssessmentCriteria.delete();
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

