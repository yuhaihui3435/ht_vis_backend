package com.ht.vis.controller.vehicle;

import com.ht.vis.core.CoreController;
import com.ht.vis.model.VChange;
import com.ht.vis.query.VChangeQuery;
import com.ht.vis.service.vehicle.VChangeService;
import com.ht.vis.service.vehicle.VInfoService;
import com.ht.vis.validator.vehicle.VChangeValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VChangeController extends CoreController {

    private VChangeService vChangeService = Duang.duang(VChangeService.class.getSimpleName(), VChangeService.class);
    private VInfoService vInfoService = Duang.duang(VInfoService.class.getSimpleName(), VInfoService.class);

    public void list() {
        VChangeQuery vChangeQuery = (VChangeQuery) getQueryModel(VChangeQuery.class);
        List<VChange> ret = vChangeService.findAll(vChangeQuery);
        renderJson(ret);
    }

    public void page() {
        VChangeQuery vChangeQuery = (VChangeQuery) getQueryModel(VChangeQuery.class);
        Page<VChange> ret = vChangeService.findPage(vChangeQuery);
        renderJson(ret);
    }

    @Before({VChangeValidator.class})
    public void save() {
        VChange vChange = getApModel(VChange.class);
        if (currUser() != null) {
            vChange.setOpId(currUser().getId());
            vChange.setCCode(currUser().getCCode());
        }
        vChangeService.save(vChange);
        renderSuccessJSON("过户记录新增成功");
    }

    @Before({VChangeValidator.class})
    public void update() {
        VChange vChange = getApModel(VChange.class);
        if (currUser() != null) {
            vChange.setOpId(currUser().getId());
        }
        vChangeService.update(vChange);
        renderSuccessJSON("过户记录修改成功");
    }

    //逻辑删除
    @Before({VChangeValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        vChangeService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("过户记录删除成功");
    }

    //真实删除
    @Before({VChangeValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        vChangeService.batchDel(ids);
        renderSuccessJSON("过户记录删除成功");
    }

    public void get() {
        Integer id = getParaToInt("id");
        renderJson(vChangeService.findOne(id));
    }

    public void init() {
        Map<String, Object> ret = new HashMap<>();
        ret.put(VInfoService.CACHEKEY_ALLVINFO, vInfoService.findAllInCache());
        renderJson(ret);
    }
}