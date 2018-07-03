package com.ht.vis.controller.company;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreController;
import com.ht.vis.kits.ext.BCrypt;
import com.ht.vis.model.CInfo;
import com.ht.vis.model.User;
import com.ht.vis.query.CInfoQuery;
import com.ht.vis.service.company.CInfoService;
import com.ht.vis.service.user.UserService;
import com.ht.vis.validator.company.CInfoValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CInfoController extends CoreController {

    private CInfoService cInfoService = Duang.duang(CInfoService.class.getSimpleName(), CInfoService.class);
    private UserService userService=Duang.duang(UserService.class.getSimpleName(),UserService.class);
    public void list() {
        CInfoQuery cInfoQuery = (CInfoQuery) getQueryModel(CInfoQuery.class);
        List<CInfo> ret = cInfoService.findAll(cInfoQuery);
        renderJson(ret);
    }

    public void page() {
        CInfoQuery cInfoQuery = (CInfoQuery) getQueryModel(CInfoQuery.class);
        Page<CInfo> ret = cInfoService.findPage(cInfoQuery);
        renderJson(ret);
    }

    @Before({CInfoValidator.class})
    public void save() {
        CInfo cInfo = getApModel(CInfo.class);
        if (currUser() != null) {
            cInfo.setOpId(currUser().getId());
        }
        cInfoService.saveAndCreateUser(cInfo);

        renderSuccessJSON("企业信息保存成功");
    }

    @Before({CInfoValidator.class})
    public void update() {
        CInfo cInfo = getApModel(CInfo.class);
        if (currUser() != null) {
            cInfo.setOpId(currUser().getId());
        }
        cInfoService.update(cInfo);
        renderSuccessJSON("企业信息修改成功");
    }

    //逻辑删除
    @Before({CInfoValidator.class})
    public void logicDel() {
        Integer[] ids = getParaValuesToInt("ids");
        cInfoService.batchLogicDel(ids, currUser() == null ? null : currUser().getId());
        renderSuccessJSON("企业信息删除成功");
    }

    //真实删除
    @Before({CInfoValidator.class})
    public void del() {
        Integer[] ids = getParaValuesToInt("ids");
        cInfoService.batchDel(ids);
        renderSuccessJSON("企业信息删除成功");
    }

    public void get() {
        Integer id = getParaToInt("id");
        renderJson(cInfoService.findOne(id));
    }

    public void init() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("cTypeList", CacheKit.get(Consts.CACHE_NAMES.dd.name(),"cTypeList"));
        renderJson(ret);
    }

    public void getCurrCInfo(){
        String cCode=currUser().getCCode();
        CInfo ret=null;
        if(StrUtil.isNotBlank(cCode)){
            ret=cInfoService.findByCode(cCode);
        }
        renderJson(ret);
    }
}