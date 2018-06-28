package com.ht.vis.service.vehicle;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.core.CoreService;
import com.ht.vis.kits.DateKit;
import com.ht.vis.model.VInsurance;
import com.ht.vis.query.VInsuranceQuery;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.Date;
import java.util.List;

public class VInsuranceService extends CoreService {

    private static VInsurance vInsuranceDao = VInsurance.dao;

    public List<VInsurance> findAll(VInsuranceQuery vInsuranceQuery){
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(vInsuranceQuery.getLicensePlate())){
            kv.put("licensePlate=",vInsuranceQuery.getLicensePlate());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getBeginBAt())){
            kv.put("bAt >=",vInsuranceQuery.getBeginBAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getEndBAt())){
            kv.put("bAt <=",vInsuranceQuery.getEndBAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getBeginEAt())){
            kv.put("eAt >=",vInsuranceQuery.getBeginEAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getEndEAt())){
            kv.put("eAt <=",vInsuranceQuery.getEndEAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getNum())){
            kv.put("num=",vInsuranceQuery.getNum());
        }
        kv.put("dAt","");
        if(StrUtil.isNotBlank(vInsuranceQuery.getOrderBy())) {
            kv.put("orderBy", vInsuranceQuery.getOrderBy());
        }
        kv=VInsurance.buildParamMap(VInsurance.class,kv);
        return vInsuranceDao.findByAndCond(kv);
    }


    public Page<VInsurance> findPage(VInsuranceQuery vInsuranceQuery){
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(vInsuranceQuery.getLicensePlate())){
            kv.put("licensePlate=",vInsuranceQuery.getLicensePlate());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getBeginBAt())){
            kv.put("bAt >=",vInsuranceQuery.getBeginBAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getEndBAt())){
            kv.put("bAt <=",vInsuranceQuery.getEndBAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getBeginEAt())){
            kv.put("eAt >=",vInsuranceQuery.getBeginEAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getEndEAt())){
            kv.put("eAt <=",vInsuranceQuery.getEndEAt());
        }
        if(StrUtil.isNotBlank(vInsuranceQuery.getNum())){
            kv.put("num=",vInsuranceQuery.getNum());
        }
        kv.put("dAt","");
        if(StrUtil.isNotBlank(vInsuranceQuery.getOrderBy())) {
            kv.put("orderBy", vInsuranceQuery.getOrderBy());
        }
        kv=VInsurance.buildParamMap(VInsurance.class,kv);
        return vInsuranceDao.pageByAndCond(kv,vInsuranceQuery.getPn(),vInsuranceQuery.getPs());
    }

    public VInsurance findOne(Integer id) {
        return vInsuranceDao.findById(id);
    }

    @Before({Tx.class})
    public void save(VInsurance vInsurance) {
        vInsurance.save();
    }

    @Before({Tx.class})
    public void update(VInsurance vInsurance) {
        vInsurance.update();
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        VInsurance vInsurance = findOne(id);

        if (opId != null) {
            vInsurance.setOpId(opId);
        }

        vInsurance.apDel();

    }

    @Before({Tx.class})
    public void del(Integer id) {
        VInsurance vInsurance = findOne(id);
        vInsurance.delete();
    }

    @Before({Tx.class})
    public void batchLogicDel(Integer[] ids, Integer opId) {
        if (ids != null) {
            for (Integer id : ids) {
                logicDel(id, opId);
            }
        }
    }

    @Before({Tx.class})
    public void batchDel(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                del(id);
            }
        }
    }
    //保险日期是否重复
    public List<VInsurance> findByLicensePlateInDate(VInsurance vInsurance) {
        Kv kv = Kv.create();
        kv.put("bAt", vInsurance.getBAt());
        kv.put("eAt",vInsurance.getEAt());
        kv.put("licensePlate",vInsurance.getLicensePlate());
        SqlPara sqlPara=Db.getSqlPara("vInsuranceSql.findByLicensePlateInDate",kv);
        return vInsuranceDao.find(sqlPara);
    }
    //保险日期是否重复
    public List<VInsurance> findByLicensePlateInDateNeId(VInsurance vInsurance) {
        Kv kv = Kv.create();
        kv.put("bAt", vInsurance.getBAt());
        kv.put("eAt",vInsurance.getEAt());
        kv.put("id",vInsurance.getId());
        kv.put("licensePlate",vInsurance.getLicensePlate());
        SqlPara sqlPara=Db.getSqlPara("vInsuranceSql.findByLicensePlateInDateNeId",kv);
        return vInsuranceDao.find(sqlPara);
    }

}

