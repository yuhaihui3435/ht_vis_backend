package com.ht.vis.service.vehicle;

import com.ht.vis.core.CoreService;
import com.ht.vis.model.VLine;
import com.ht.vis.query.VLineQuery;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;

import java.util.*;

import com.jfinal.plugin.activerecord.Page;

public class VLineService extends CoreService {

    private static VLine vLineDao = VLine.dao;

    public List<VLine> findAll(VLineQuery vLineQuery) {
        Kv kv = Kv.create();
        if(StrUtil.isNotBlank(vLineQuery.getcCode())){
            kv.put("cCode=",vLineQuery.getcCode());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(vLineQuery.getOrderBy())) {
            kv.put("orderBy", vLineQuery.getOrderBy());
        }
        kv = VLine.buildParamMap(VLine.class, kv);
        return vLineDao.findByAndCond(kv);
    }


    public Page<VLine> findPage(VLineQuery vLineQuery) {
        Kv kv = Kv.create();
        kv.put("dAt", "");
        if(StrUtil.isNotBlank(vLineQuery.getcCode())){
            kv.put("cCode=",vLineQuery.getcCode());
        }
        if (StrUtil.isNotBlank(vLineQuery.getOrderBy())) {
            kv.put("orderBy", vLineQuery.getOrderBy());
        }
        kv = VLine.buildParamMap(VLine.class, kv);
        return vLineDao.pageByAndCond(kv, vLineQuery.getPn(), vLineQuery.getPs());
    }

    public VLine findOne(Integer id) {
        return vLineDao.findById(id);
    }

    @Before({Tx.class})
    public void save(VLine vLine) {
        vLine.save();
    }

    @Before({Tx.class})
    public void update(VLine vLine) {
        vLine.update();
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        VLine vLine = findOne(id);

        if (opId != null) {
            vLine.setOpId(opId);
        }

        vLine.apDel();

    }

    @Before({Tx.class})
    public void del(Integer id) {
        VLine vLine = findOne(id);
        vLine.delete();
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

    public List<VLine> findByCCode(String cCode){
        return vLineDao.findByPropEQWithDat("cCode",cCode);
    }
}

