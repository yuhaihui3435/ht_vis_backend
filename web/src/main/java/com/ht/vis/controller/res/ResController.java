package com.ht.vis.controller.res;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.ht.vis.core.CoreController;
import com.ht.vis.model.Res;
import com.ht.vis.query.ResQuery;
import com.ht.vis.service.res.ResService;
import com.ht.vis.validator.res.ResValidator;

import java.util.List;


public class ResController extends CoreController {

    private ResService resService = Duang.duang(ResService.class.getSimpleName(), ResService.class);

    public void list() {
        ResQuery resQuery = (ResQuery) getQueryModel(ResQuery.class);
        List<Res> ret = resService.findAll(resQuery);
        renderJson(ret);
    }

    public void page() {
        ResQuery resQuery = (ResQuery) getQueryModel(ResQuery.class);
        Page<Res> ret = resService.findPage(resQuery);
        renderJson(ret);
    }

    @Before({ResValidator.class})
    public void save() {
        Res res = getApModel(Res.class);
        if (currUser() != null) {
            res.setOpId(currUser().getId());
        }
        resService.save(res);
        renderSuccessJSON("菜单数据新增成功");
    }

    @Before({ResValidator.class})
    public void update() {
        Res res = getApModel(Res.class);
        if (currUser() != null) {
            res.setOpId(currUser().getId());
        }
        resService.update(res);
        renderSuccessJSON("菜单数据修改成功");
    }

    //逻辑删除
    @Before({ResValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        resService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("菜单数据删除成功");
    }

    //真实删除
    @Before({ResValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        resService.batchDel(ids);
        renderSuccessJSON("菜单数据删除成功");
    }

    public void get() {
        Integer id = getParaToInt("id");
        renderJson(resService.findOne(id));
    }

    public void getTreeJson(){
        Integer pId=getParaToInt("pId",0);
        ResQuery resQuery=new ResQuery();
        resQuery.setPId(pId);
        List<Res> resList=resService.findAll(resQuery);
        renderJson(JSON.toJSONString(resList, SerializerFeature.DisableCircularReferenceDetect));
    }



}