package com.ht.vis.service.company;

import com.ht.vis.core.CoreService;
import com.ht.vis.model.CDepartment;
import com.ht.vis.query.CDepartmentQuery;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;

import java.util.*;

import com.jfinal.plugin.activerecord.Page;

public class CDepartmentService extends CoreService {

    private static CDepartment cDepartmentDao = CDepartment.dao;

    public List<CDepartment> findAll(CDepartmentQuery cDepartmentQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(cDepartmentQuery.getcCode())) {
            kv.put("cCode=", cDepartmentQuery.getcCode());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cDepartmentQuery.getOrderBy())) {
            kv.put("orderBy", cDepartmentQuery.getOrderBy());
        }
        kv = CDepartment.buildParamMap(CDepartment.class, kv);
        return cDepartmentDao.findByAndCond(kv);
    }


    public Page<CDepartment> findPage(CDepartmentQuery cDepartmentQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(cDepartmentQuery.getcCode())) {
            kv.put("cCode=", cDepartmentQuery.getcCode());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cDepartmentQuery.getOrderBy())) {
            kv.put("orderBy", cDepartmentQuery.getOrderBy());
        }
        kv = CDepartment.buildParamMap(CDepartment.class, kv);
        return cDepartmentDao.pageByAndCond(kv, cDepartmentQuery.getPn(), cDepartmentQuery.getPs());
    }

    public CDepartment findOne(Integer id) {
        return cDepartmentDao.findById(id);
    }

    @Before({Tx.class})
    public void save(CDepartment cDepartment) {
        cDepartment.save();
    }

    @Before({Tx.class})
    public void update(CDepartment cDepartment) {
        cDepartment.update();
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        CDepartment cDepartment = findOne(id);
        cDepartment.apDel();
    }

    @Before({Tx.class})
    public void del(Integer id) {
        CDepartment cDepartment = findOne(id);
        cDepartment.delete();
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

