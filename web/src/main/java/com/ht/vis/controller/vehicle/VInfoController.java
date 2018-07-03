package com.ht.vis.controller.vehicle;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreController;
import com.ht.vis.model.VInfo;
import com.ht.vis.query.CInfoQuery;
import com.ht.vis.query.VInfoQuery;
import com.ht.vis.service.company.CInfoService;
import com.ht.vis.service.vehicle.VInfoService;
import com.ht.vis.validator.vehicle.VInfoValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VInfoController extends CoreController {

    private VInfoService vInfoService = Duang.duang(VInfoService.class.getSimpleName(), VInfoService.class);
    private CInfoService cInfoService=Duang.duang(CInfoService.class.getSimpleName(),CInfoService.class);

    public void list() {
        VInfoQuery vInfoQuery = (VInfoQuery) getQueryModel(VInfoQuery.class);
        List<VInfo> ret = vInfoService.findAll(vInfoQuery);
        renderJson(ret);
    }

    public void page() {
        VInfoQuery vInfoQuery = (VInfoQuery) getQueryModel(VInfoQuery.class);
        Page<VInfo> ret = vInfoService.findPage(vInfoQuery);
        renderJson(JSON.toJSONString(ret,SerializerFeature.DisableCircularReferenceDetect));
    }

    @Before({VInfoValidator.class})
    public void save() {
        VInfo vInfo = getApModel(VInfo.class);
        if (currUser() != null) {
            vInfo.setOpId(currUser().getId());
            vInfo.setCCode(currUser().getCCode());
        }
        vInfo.setLicensePlate(vInfo.getLicensePlate().toUpperCase());
        vInfoService.save(vInfo);
        renderSuccessJSON("车辆信息新增成功");
    }

    @Before({VInfoValidator.class})
    public void update() {
        VInfo vInfo = getApModel(VInfo.class);
        if (currUser() != null) {
            vInfo.setOpId(currUser().getId());
        }
        vInfo.setLicensePlate(vInfo.getLicensePlate().toUpperCase());
        vInfoService.update(vInfo);
        renderSuccessJSON("车辆信息修改成功");
    }

    //逻辑删除
    @Before({VInfoValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        vInfoService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("车辆信息删除成功");
    }

    //真实删除
    @Before({VInfoValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        vInfoService.batchDel(ids);
        renderSuccessJSON("车辆信息删除成功");
    }

    public void get() {
        Integer id = getParaToInt("id");
        renderJson(vInfoService.findOne(id));
    }

    public void init() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("sBusAreaList", CacheKit.get(Consts.CACHE_NAMES.dd.name(), "sBusAreaList"));
        ret.put("busLineList", CacheKit.get(Consts.CACHE_NAMES.dd.name(), "busLineList"));
        if(StrUtil.isBlank(currUser().getCCode()))
        ret.put("cInfoList",cInfoService.findAll(new CInfoQuery()));
        renderJson(ret);
    }
}