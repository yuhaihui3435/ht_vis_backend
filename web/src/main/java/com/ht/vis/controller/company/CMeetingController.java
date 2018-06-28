package com.ht.vis.controller.company;

import com.ht.vis.core.CoreController;
import com.ht.vis.model.CMeeting;
import com.ht.vis.query.CMeetingQuery;
import com.ht.vis.service.company.CMeetingService;
import com.ht.vis.validator.company.CMeetingValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;


public class CMeetingController extends CoreController{

    private CMeetingService cMeetingService=Duang.duang(CMeetingService.class.getSimpleName(),CMeetingService.class);

    public void list(){
        CMeetingQuery cMeetingQuery=(CMeetingQuery)getQueryModel(CMeetingQuery.class);
        List<CMeeting> ret=cMeetingService.findAll(cMeetingQuery);
        renderJson(ret);
    }

    public void page(){
        CMeetingQuery cMeetingQuery=(CMeetingQuery)getQueryModel(CMeetingQuery.class);
        Page<CMeeting> ret=cMeetingService.findPage(cMeetingQuery);
        renderJson(ret);
    }
    @Before({CMeetingValidator.class})
    public void save(){
        CMeeting cMeeting=getApModel(CMeeting.class);
                if(currUser()!=null){cMeeting.setOpId(currUser().getId());}
        cMeetingService.save(cMeeting);
        renderSuccessJSON("会议记录新增成功");
    }
    @Before({CMeetingValidator.class})
    public void update(){
        CMeeting cMeeting=getApModel(CMeeting.class);
                        if(currUser()!=null){cMeeting.setOpId(currUser().getId());}
        cMeetingService.update(cMeeting);
        renderSuccessJSON("会议记录修改成功");
    }

    //逻辑删除
    @Before({CMeetingValidator.class})
    public void logicDel(){
        Integer[] ids=getParaValuesToInt("ids");
        cMeetingService.batchLogicDel(ids,currUser()==null?null:currUser().getId());
        renderSuccessJSON("会议记录删除成功");
    }

    //真实删除
    @Before({CMeetingValidator.class})
    public void del(){
        Integer[] ids=getParaValuesToInt("ids");
        cMeetingService.batchDel(ids);
        renderSuccessJSON("会议记录删除成功");
    }
    public void get(){
        Integer id=getParaToInt("id");
        renderJson(cMeetingService.findOne(id));
    }
    public void init(){
        renderJson("");
    }
}