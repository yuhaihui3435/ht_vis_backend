package com.ht.vis.controller.sp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreController;
import com.ht.vis.model.Dd;
import com.ht.vis.model.SpAssessmentCriteria;
import com.ht.vis.query.SpAssessmentCriteriaQuery;
import com.ht.vis.service.sp.SpAssessmentCriteriaService;
import com.ht.vis.validator.sp.SpAssessmentCriteriaValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpAssessmentCriteriaController extends CoreController {

    private SpAssessmentCriteriaService spAssessmentCriteriaService = Duang.duang(SpAssessmentCriteriaService.class.getSimpleName(), SpAssessmentCriteriaService.class);

    public void list() {
        SpAssessmentCriteriaQuery spAssessmentCriteriaQuery = (SpAssessmentCriteriaQuery) getQueryModel(SpAssessmentCriteriaQuery.class);
        List<SpAssessmentCriteria> ret = spAssessmentCriteriaService.findAll(spAssessmentCriteriaQuery);
        renderJson(JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect));
    }

    public void page() {
        SpAssessmentCriteriaQuery spAssessmentCriteriaQuery = (SpAssessmentCriteriaQuery) getQueryModel(SpAssessmentCriteriaQuery.class);
        Page<SpAssessmentCriteria> ret = spAssessmentCriteriaService.findPage(spAssessmentCriteriaQuery);
        renderJson(JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Before({SpAssessmentCriteriaValidator.class})
    public void save() {
        SpAssessmentCriteria spAssessmentCriteria = getApModel(SpAssessmentCriteria.class);
        if (currUser() != null) {
            spAssessmentCriteria.setOpId(currUser().getId());
        }
        spAssessmentCriteriaService.save(spAssessmentCriteria);
        renderSuccessJSON("考评标准新增成功");
    }

    @Before({SpAssessmentCriteriaValidator.class})
    public void update() {
        SpAssessmentCriteria spAssessmentCriteria = getApModel(SpAssessmentCriteria.class);
        if (currUser() != null) {
            spAssessmentCriteria.setOpId(currUser().getId());
        }
        spAssessmentCriteriaService.update(spAssessmentCriteria);
        renderSuccessJSON("考评标准修改成功");
    }

    //逻辑删除
    @Before({SpAssessmentCriteriaValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        spAssessmentCriteriaService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("考评标准删除成功");
    }

    //真实删除
    @Before({SpAssessmentCriteriaValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        spAssessmentCriteriaService.batchDel(ids);
        renderSuccessJSON("考评标准删除成功");
    }

    public void get() {
        Integer id = getParaToInt("id");
        renderJson(spAssessmentCriteriaService.findOne(id));
    }

    public void init() {
        Map<String, Object> ret = new HashMap<>();
        Map<String,List<Dd>> normChildMap=new HashMap<>();
        List<Dd> normList=CacheKit.get(Consts.CACHE_NAMES.dd.name(),"normList");
        for (Dd dd:normList){
            normChildMap.put(dd.getId().toString(),(List<Dd>)CacheKit.get(Consts.CACHE_NAMES.dd.name(),dd.getVal()+"List"));
        }
        ret.put("normList",normList);
        ret.put("normChildMap",normChildMap);
        renderJson(ret);
    }
}