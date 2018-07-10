package com.ht.vis.controller.vehicle;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ht.vis.core.CoreController;
import com.ht.vis.model.VLine;
import com.ht.vis.query.CInfoQuery;
import com.ht.vis.query.VLineQuery;
import com.ht.vis.service.company.CInfoService;
import com.ht.vis.service.vehicle.VLineService;
import com.ht.vis.validator.vehicle.VLineValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VLineController extends CoreController {

    private VLineService vLineService = Duang.duang(VLineService.class.getSimpleName(), VLineService.class);
    private CInfoService cInfoService = Duang.duang(CInfoService.class.getSimpleName(), CInfoService.class);

    public void list() {
        VLineQuery vLineQuery = (VLineQuery) getQueryModel(VLineQuery.class);
        if (currUser() != null) {
            if(StrUtil.isNotBlank(currUser().getCCode()))
            vLineQuery.setcCode(currUser().getCCode());
        }
        List<VLine> ret = vLineService.findAll(vLineQuery);
        renderJson(JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect));
    }

    public void page() {
        VLineQuery vLineQuery = (VLineQuery) getQueryModel(VLineQuery.class);
        if (currUser() != null) {
            if(StrUtil.isNotBlank(currUser().getCCode()))
            vLineQuery.setcCode(currUser().getCCode());
        }
        Page<VLine> ret = vLineService.findPage(vLineQuery);
        renderJson(JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Before({VLineValidator.class})
    public void save() {
        VLine vLine = getApModel(VLine.class);
        if (currUser() != null) {
            vLine.setOpId(currUser().getId());
            if(StrUtil.isNotBlank(currUser().getCCode()))
                vLine.setCCode(currUser().getCCode());
        }
        vLineService.save(vLine);
        renderSuccessJSON("线路新增成功");
    }

    @Before({VLineValidator.class})
    public void update() {
        VLine vLine = getApModel(VLine.class);
        if (currUser() != null) {
            vLine.setOpId(currUser().getId());
            if(StrUtil.isNotBlank(currUser().getCCode()))
                vLine.setCCode(currUser().getCCode());
        }
        vLineService.update(vLine);
        renderSuccessJSON("线路修改成功");
    }

    //逻辑删除
    @Before({VLineValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        vLineService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("线路删除成功");
    }

    //真实删除
    @Before({VLineValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        vLineService.batchDel(ids);
        renderSuccessJSON("线路删除成功");
    }

    public void get() {
        Integer id = getParaToInt("id");
        renderJson(vLineService.findOne(id));
    }

    public void init() {
        Map<String, Object> ret = new HashMap<>();
        if (StrUtil.isBlank(currUser().getCCode()))
            ret.put("cInfoList", cInfoService.findAll(new CInfoQuery()));
        renderJson(ret);
    }
}