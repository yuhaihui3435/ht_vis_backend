package com.ht.vis.interceptors;

import cn.hutool.http.HttpUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.JsonKit;
import com.jfinal.render.JsonRender;
import com.ht.vis.Consts;
import com.ht.vis.model.LogOp;
import com.ht.vis.model.User;
import com.ht.vis.core.CoreController;

import java.util.Date;
import java.util.Map;

import static cn.hutool.core.util.StrUtil.isNotBlank;

public class OpLogInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        CoreController coreController=(CoreController) inv.getController();
        User user=(User)coreController.getAttr(Consts.CURR_USER);
        String channel=coreController.getPara("channel");//渠道标识
        String method=inv.getController().getClass().getCanonicalName()+"."+inv.getMethodName();
        String ip= HttpUtil.getClientIP(coreController.getRequest());
        Map map=coreController.getParaMap();
        String param= JsonKit.toJson(map);
        LogOp logOp=new LogOp();
        logOp.setOpName(user==null?"":user.getLoginname());
        logOp.setOpChannel(isNotBlank(channel)?channel:"local");
        logOp.setReqMethod(method);
        logOp.setReqAt(new Date());
        logOp.setReqIp(ip);
        logOp.setReqParam(param);
        inv.invoke();
        JsonRender jsonRender= (JsonRender) coreController.getRender();
        logOp.setReqRet(jsonRender!=null?jsonRender.getJsonText():null);
        logOp.save();
    }


}