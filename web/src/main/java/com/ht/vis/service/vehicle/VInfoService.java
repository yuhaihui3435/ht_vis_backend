package com.ht.vis.service.vehicle;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.VInfo;
import com.ht.vis.model.VInsurance;
import com.ht.vis.query.VInfoQuery;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

public class VInfoService extends CoreService {

    private static VInfo vInfoDao = VInfo.dao;

    public static final String CACHEKEY_ALLVINFO="allVInfo";

    public List<VInfo> findAll(VInfoQuery vInfoQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(vInfoQuery.getLicensePlate())) {
            kv.put("licensePlate like", "%" + vInfoQuery.getLicensePlate() + "%");
        }
        if (StrUtil.isNotBlank(vInfoQuery.getHost())) {
            kv.put("host like", "%" + vInfoQuery.getHost() + "%");
        }
        if (StrUtil.isNotBlank(vInfoQuery.getType())) {
            kv.put("type=", vInfoQuery.getType());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getTel())) {
            kv.put("tel=", vInfoQuery.getTel());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getTelx())) {
            kv.put("telx=", vInfoQuery.getTelx());
        }
        if (vInfoQuery.getRegDate() != null) {
            kv.put("regDate=", vInfoQuery.getRegDate());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getLine())) {
            kv.put("line=", vInfoQuery.getLine());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getArea())) {
            kv.put("area=", vInfoQuery.getArea());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getIdcard())) {
            kv.put("idcard=", vInfoQuery.getIdcard());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(vInfoQuery.getOrderBy())) {
            kv.put("orderBy", vInfoQuery.getOrderBy());
        }
        kv = VInfo.buildParamMap(VInfo.class, kv);
        return vInfoDao.findByAndCond(kv);
    }


    public Page<VInfo> findPage(VInfoQuery vInfoQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(vInfoQuery.getLicensePlate())) {
            kv.put("licensePlate like", "%" + vInfoQuery.getLicensePlate() + "%");
        }
        if (StrUtil.isNotBlank(vInfoQuery.getHost())) {
            kv.put("host like", "%" + vInfoQuery.getHost() + "%");
        }
        if (StrUtil.isNotBlank(vInfoQuery.getType())) {
            kv.put("type=", vInfoQuery.getType());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getTel())) {
            kv.put("tel=", vInfoQuery.getTel());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getTelx())) {
            kv.put("telx=", vInfoQuery.getTelx());
        }
        if (vInfoQuery.getRegDate() != null) {
            kv.put("regDate=", vInfoQuery.getRegDate());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getLine())) {
            kv.put("line=", vInfoQuery.getLine());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getArea())) {
            kv.put("area=", vInfoQuery.getArea());
        }
        if (StrUtil.isNotBlank(vInfoQuery.getIdcard())) {
            kv.put("idcard=", vInfoQuery.getIdcard());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(vInfoQuery.getOrderBy())) {
            kv.put("orderBy", vInfoQuery.getOrderBy());
        }
        kv = VInfo.buildParamMap(VInfo.class, kv);
        return vInfoDao.pageByAndCond(kv, vInfoQuery.getPn(), vInfoQuery.getPs());
    }

    public VInfo findOne(Integer id) {
        return vInfoDao.findById(id);
    }

    @Before({Tx.class})
    public void save(VInfo vInfo) {
        vInfo.save();
        CacheKit.remove(VInfo.class.getName(),CACHEKEY_ALLVINFO);
    }

    @Before({Tx.class})
    public void update(VInfo vInfo) {
        vInfo.update();
        CacheKit.remove(VInfo.class.getName(),CACHEKEY_ALLVINFO);
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        VInfo vInfo = findOne(id);

        if (opId != null) {
            vInfo.setOpId(opId);
        }

        vInfo.apDel();
        CacheKit.remove(VInfo.class.getName(),CACHEKEY_ALLVINFO);
    }

    @Before({Tx.class})
    public void del(Integer id) {
        VInfo vInfo = findOne(id);
        vInfo.delete();
        CacheKit.remove(VInfo.class.getName(),CACHEKEY_ALLVINFO);
    }

    @Before({Tx.class})
    public void batchLogicDel(Integer[] ids, Integer opId) {
        if (ids != null) {
            for (Integer id : ids) {
                logicDel(id, opId);
            }
        }
        CacheKit.remove(VInfo.class.getName(),CACHEKEY_ALLVINFO);
    }

    @Before({Tx.class})
    public void batchDel(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                del(id);
            }
        }
        CacheKit.remove(VInfo.class.getName(),CACHEKEY_ALLVINFO);
    }
    //查询所有车牌照号缓存
    public List<VInfo> findAllInCache(){
        return vInfoDao.findByCache(VInfo.class.getSimpleName(),CACHEKEY_ALLVINFO,"select * from v_info where dAt is null and status='0'");
    }


}

