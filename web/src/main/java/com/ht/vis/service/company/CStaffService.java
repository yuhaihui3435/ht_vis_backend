package com.ht.vis.service.company;

import com.ht.vis.core.CoreService;
import com.ht.vis.model.CDepartmentStaff;
import com.ht.vis.model.CStaff;
import com.ht.vis.query.CStaffQuery;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;

import java.util.*;

import com.jfinal.plugin.activerecord.Page;

public class CStaffService extends CoreService {

    private static CStaff cStaffDao = CStaff.dao;

    public List<CStaff> findAll(CStaffQuery cStaffQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(cStaffQuery.getName())) {
            kv.put("name like", "%" + cStaffQuery.getName() + "%");
        }
        if (StrUtil.isNotBlank(cStaffQuery.getCode())) {
            kv.put("code=", cStaffQuery.getCode());
        }
        if (StrUtil.isNotBlank(cStaffQuery.getTel())) {
            kv.put("tel=", cStaffQuery.getTel());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cStaffQuery.getOrderBy())) {
            kv.put("orderBy", cStaffQuery.getOrderBy());
        }
        kv = CStaff.buildParamMap(CStaff.class, kv);
        return cStaffDao.findByAndCond(kv);
    }


    public Page<CStaff> findPage(CStaffQuery cStaffQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(cStaffQuery.getName())) {
            kv.put("name like", "%" + cStaffQuery.getName() + "%");
        }
        if (StrUtil.isNotBlank(cStaffQuery.getCode())) {
            kv.put("code=", cStaffQuery.getCode());
        }
        if (StrUtil.isNotBlank(cStaffQuery.getTel())) {
            kv.put("tel=", cStaffQuery.getTel());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cStaffQuery.getOrderBy())) {
            kv.put("orderBy", cStaffQuery.getOrderBy());
        }
        kv = CStaff.buildParamMap(CStaff.class, kv);
        return cStaffDao.pageByAndCond(kv, cStaffQuery.getPn(), cStaffQuery.getPs());
    }

    public CStaff findOne(Integer id) {
        return cStaffDao.findById(id);
    }

    @Before({Tx.class})
    public void save(CStaff cStaff) {
        cStaff.save();
    }

    @Before({Tx.class})
    public void update(CStaff cStaff) {
        cStaff.update();
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        CStaff cStaff = findOne(id);


        cStaff.apDel();

    }

    @Before({Tx.class})
    public void del(Integer id) {
        CStaff cStaff = findOne(id);
        cStaff.delete();
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

    public List<CDepartmentStaff> findDJbyCode(String staffCode){
        return CDepartmentStaff.dao.findByPropEQ("sCode",staffCode);
    }
}

