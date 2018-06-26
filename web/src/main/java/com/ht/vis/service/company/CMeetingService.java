package com.ht.vis.service.company;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.CMeeting;
import com.ht.vis.query.CMeetingQuery;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.List;

public class CMeetingService extends CoreService {

    private static CMeeting cMeetingDao = CMeeting.dao;

    public List<CMeeting> findAll(CMeetingQuery cMeetingQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(cMeetingQuery.getTitle())) {
            kv.put("title like", "%" + cMeetingQuery.getTitle() + "%");
        }
        if (StrUtil.isNotBlank(cMeetingQuery.getType())) {
            kv.put("type=", cMeetingQuery.getType());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cMeetingQuery.getOrderBy())) {
            kv.put("orderBy", cMeetingQuery.getOrderBy());
        }
        kv = CMeeting.buildParamMap(CMeeting.class, kv);
        return cMeetingDao.findByAndCond(kv);
    }


    public Page<CMeeting> findPage(CMeetingQuery cMeetingQuery) {
        Kv kv = Kv.create();
        if (StrUtil.isNotBlank(cMeetingQuery.getTitle())) {
            kv.put("title like", "%" + cMeetingQuery.getTitle() + "%");
        }
        if (StrUtil.isNotBlank(cMeetingQuery.getType())) {
            kv.put("type=", cMeetingQuery.getType());
        }
        kv.put("dAt", "");
        if (StrUtil.isNotBlank(cMeetingQuery.getOrderBy())) {
            kv.put("orderBy", cMeetingQuery.getOrderBy());
        }
        kv = CMeeting.buildParamMap(CMeeting.class, kv);
        return cMeetingDao.pageByAndCond(kv, cMeetingQuery.getPn(), cMeetingQuery.getPs());
    }

    public CMeeting findOne(Integer id) {
        return cMeetingDao.findById(id);
    }

    @Before({Tx.class})
    public void save(CMeeting cMeeting) {
        cMeeting.save();
    }

    @Before({Tx.class})
    public void update(CMeeting cMeeting) {
        cMeeting.update();
    }

    @Before({Tx.class})
    public void logicDel(Integer id, Integer opId) {
        CMeeting cMeeting = findOne(id);

        if (opId != null) {
            cMeeting.setOpId(opId);
        }

        cMeeting.apDel();

    }

    @Before({Tx.class})
    public void del(Integer id) {
        CMeeting cMeeting = findOne(id);
        cMeeting.delete();
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

