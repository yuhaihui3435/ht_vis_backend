package com.ht.vis.service.vehicle;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.VChange;
import com.ht.vis.query.VChangeQuery;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.List;

public class VChangeService extends CoreService {

    private static VChange vChangeDao = VChange.dao;

    public List<VChange> findAll(VChangeQuery vChangeQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(vChangeQuery.getLicensePlate())) {
            kv.put("licensePlate=", vChangeQuery.getLicensePlate());
        }
        if (StrUtil.isNotBlank(vChangeQuery.getBeginChangeDate())) {
            kv.put("changeDate >=", vChangeQuery.getBeginChangeDate());
        }
        if (StrUtil.isNotBlank(vChangeQuery.getEndChangeDate())) {
            kv.put("changeDate <=", vChangeQuery.getEndChangeDate());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(vChangeQuery.getOrderBy())) {
            kv.put("orderBy", vChangeQuery.getOrderBy());
        }
        kv = VChange.buildParamMap(VChange.class, kv);
        return vChangeDao.findByAndCond(kv);
    }


    public Page<VChange> findPage(VChangeQuery vChangeQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(vChangeQuery.getLicensePlate())) {
            kv.put("licensePlate=", vChangeQuery.getLicensePlate());
        }
        if (StrUtil.isNotBlank(vChangeQuery.getBeginChangeDate())) {
            kv.put("changeDate >=", vChangeQuery.getBeginChangeDate());
        }
        if (StrUtil.isNotBlank(vChangeQuery.getEndChangeDate())) {
            kv.put("changeDate <=", vChangeQuery.getEndChangeDate());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(vChangeQuery.getOrderBy())) {
            kv.put("orderBy", vChangeQuery.getOrderBy());
        }
        kv = VChange.buildParamMap(VChange.class, kv);
        return vChangeDao.pageByAndCond(kv, vChangeQuery.getPn(), vChangeQuery.getPs());
    }

    public VChange findOne(Integer id) {
        return vChangeDao.findById(id);
    }

    @Before({Tx.class})
    public void save(VChange vChange) {
        vChange.save();
    }

    @Before({Tx.class})
    public void update(VChange vChange) {
        vChange.update();
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        VChange vChange = findOne(id);

        if (opId != null) {
            vChange.setOpId(opId);
        }

        vChange.apDel();

    }

    @Before({Tx.class})
    public void del(Integer id) {
        VChange vChange = findOne(id);
        vChange.delete();
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
}

