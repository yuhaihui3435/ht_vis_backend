package com.ht.vis.service.company;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreService;
import com.ht.vis.kits.ext.BCrypt;
import com.ht.vis.model.CInfo;
import com.ht.vis.model.User;
import com.ht.vis.model.UserRole;
import com.ht.vis.query.CInfoQuery;
import cn.hutool.core.util.StrUtil;
import com.ht.vis.service.user.UserService;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;

import java.util.*;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

public class CInfoService extends CoreService {

    private static CInfo cInfoDao = CInfo.dao;

    private UserService userService = Duang.duang(UserService.class.getSimpleName(), UserService.class);

    public List<CInfo> findAll(CInfoQuery cInfoQuery) {
        Kv kv = Kv.create();
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cInfoQuery.getOrderBy())) {
            kv.put("orderBy", cInfoQuery.getOrderBy());
        }
        kv = CInfo.buildParamMap(CInfo.class, kv);
        return cInfoDao.findByAndCond(kv);
    }


    public Page<CInfo> findPage(CInfoQuery cInfoQuery) {
        Kv kv = Kv.create();
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cInfoQuery.getOrderBy())) {
            kv.put("orderBy", cInfoQuery.getOrderBy());
        }
        kv = CInfo.buildParamMap(CInfo.class, kv);
        return cInfoDao.pageByAndCond(kv, cInfoQuery.getPn(), cInfoQuery.getPs());
    }

    public CInfo findOne(Integer id) {
        return cInfoDao.findById(id);
    }

    @Before({Tx.class})
    public void save(CInfo cInfo) {
        cInfo.save();
    }

    @Before({Tx.class})
    public void update(CInfo cInfo) {
        cInfo.update();
        CacheKit.remove(CInfo.class.getSimpleName(),"code_"+cInfo.getCode());
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        CInfo cInfo = findOne(id);

        if (opId != null) {
            cInfo.setOpId(opId);
        }

        cInfo.apDel();
        CacheKit.remove(CInfo.class.getSimpleName(),"code_"+cInfo.getCode());
    }

    @Before({Tx.class})
    public void del(Integer id) {
        CInfo cInfo = findOne(id);
        cInfo.delete();
        CacheKit.remove(CInfo.class.getSimpleName(),"code_"+cInfo.getCode());
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

    public CInfo findByCode(String code) {
        return cInfoDao.findFirstByPropEQ("code", code);
    }

    public CInfo findByCodeInCache(String code)
    {
      return cInfoDao.findByCodeInCache(code);
    }
    @Before({Tx.class})
    public void saveAndCreateUser(CInfo cInfo) {
        cInfo.setAccountExpiryDate(DateUtil.offsetDay(DateUtil.date(),365));
        save(cInfo);
        User user = new User();
        user.setCCode(cInfo.getCode());
        user.setLoginname(cInfo.getCode() + "@oper");
        user.setNickname(RandomUtil.randomString(6));
        user.setPhone(cInfo.getTel());
        String orgPwd = StrUtil.sub(user.getPhone(), user.getPhone().length() - 6, user.getPhone().length());
        user.setSalt(BCrypt.gensalt());
        user.setPassword(BCrypt.hashpw(orgPwd, user.getSalt()));
        user.setStatus(Consts.YORN_STR.yes.getVal());

        UserRole userRole=new UserRole();
        userRole.setLoginname(user.getLoginname());
        userRole.setRoleCode("cOper");
        userRole.save();

        userService.save(user);
    }
}

