package com.ht.vis.interceptors;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreController;
import com.ht.vis.core.CoreException;
import com.ht.vis.kits.ReqKit;
import com.ht.vis.model.CInfo;
import com.ht.vis.model.User;
import com.ht.vis.service.company.CInfoService;
import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class CInfoInterceptor implements Interceptor {
    public static final String ERR_MSG="企业账户已经过期，请联系系统管理员续费。";
    private CInfoService cInfoService= Duang.duang(CInfoService.class.getSimpleName(),CInfoService.class);
    @Override
    public void intercept(Invocation invocation) {

        CoreController controller = (CoreController)invocation.getController();
        User currUser=controller.getAttr(Consts.CURR_USER);
        String cCode= currUser.getCCode();
        if(StrUtil.isNotBlank(cCode)) {
            CInfo cInfo = cInfoService.findByCodeInCache(cCode);

            if (cInfo.getAccountExpiryDate()!=null&&DateUtil.date().after(cInfo.getAccountExpiryDate())) {
                controller.setAttr("cCode",cInfo.getCode());
                controller.setAttr(Consts.CURR_USER_CINFO, cInfo);
                invocation.invoke();
            } else {
                if (ReqKit.isAjaxRequest(controller.getRequest())) {
                    controller.renderAuth999(ERR_MSG);
                } else {
                    throw new CoreException(ERR_MSG);
                }

            }
        }else{
            invocation.invoke();
        }
    }
}
