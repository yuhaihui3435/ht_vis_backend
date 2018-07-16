package com.ht.vis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ht.vis.core.CoreController;
import com.ht.vis.model.SFileStorage;
import com.ht.vis.query.SFileStorageQuery;
import com.ht.vis.service.SFileStorageService;
import com.ht.vis.validator.SFileStorageValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SFileStorageController extends CoreController {

    private SFileStorageService sFileStorageService = Duang.duang(SFileStorageService.class.getSimpleName(), SFileStorageService.class);

    public void list() {
        SFileStorageQuery sFileStorageQuery = (SFileStorageQuery) getQueryModel(SFileStorageQuery.class);
        List<SFileStorage> ret = sFileStorageService.findAll(sFileStorageQuery);
        renderJson(JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect));
    }

    public void page() {
        SFileStorageQuery sFileStorageQuery = (SFileStorageQuery) getQueryModel(SFileStorageQuery.class);
        Page<SFileStorage> ret = sFileStorageService.findPage(sFileStorageQuery);
        renderJson(JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Before({SFileStorageValidator.class})
    public void save() {
        SFileStorage sFileStorage = getApModel(SFileStorage.class);
        sFileStorageService.save(sFileStorage);
        renderSuccessJSON("新增成功");
    }

    @Before({SFileStorageValidator.class})
    public void update() {
        SFileStorage sFileStorage = getApModel(SFileStorage.class);
        sFileStorageService.update(sFileStorage);
        renderSuccessJSON("修改成功");
    }

    //逻辑删除
    @Before({SFileStorageValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        sFileStorageService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("删除成功");
    }

    //真实删除
    @Before({SFileStorageValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        sFileStorageService.batchDel(ids);
        renderSuccessJSON("删除成功");
    }

    public void get() {
        Integer id = getParaToInt("id");
        renderJson(sFileStorageService.findOne(id));
    }

    public void init() {
        Map<String, Object> ret = new HashMap<>();

        renderJson(ret);
    }
}