package com.ht.vis.interceptors;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreController;
import com.ht.vis.core.CoreException;
import com.ht.vis.kits.CookieKit;
import com.ht.vis.kits.ReqKit;
import com.ht.vis.model.User;
import com.ht.vis.service.role.RoleService;
import com.ht.vis.service.ser.SerService;
import com.ht.vis.service.user.UserService;
import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 *
 * 请求访问控制前置处理
 *
 */
public class AdminAAuthInterceptor implements Interceptor{
    private RoleService roleService= Duang.duang(RoleService.class.getSimpleName(),RoleService.class);
    private SerService serService= Duang.duang(SerService.class.getSimpleName(),SerService.class);
    private UserService userService= Duang.duang(UserService.class.getSimpleName(),UserService.class);

    @Override
    public void intercept(Invocation invocation) {
        CoreController controller=(CoreController) invocation.getController();
        String ak=invocation.getActionKey();
        HttpServletRequest request=controller.getRequest();
        boolean flag=false;
        String userId = CookieKit.get(controller, Consts.USER_ACCESS_TOKEN);
        if(!StrUtil.isBlank(userId)) {
            User user = userService.findByIdInCache(new Integer(userId));
            String[] roleCodes = roleService.findCodesByLoginnameInCache(user.getLoginname());
            Set<String> serSet = serService.findUrlByRoleCodesInCache(roleCodes);
            Set<String> allSer = CacheKit.get(Consts.CACHE_NAMES.allSer.name(), "allSer");
            if (!allSer.contains(ak)) {
                invocation.invoke();//如果服务未配置 直接放行
                return;
            }
            if (serSet != null && serSet.contains(ak)) {
                flag = true;
            }
        }

        if(flag) {
            invocation.invoke();
        } else {
            if(ReqKit.isAjaxRequest(controller.getRequest())){
                controller.renderAuth403(null);
            }else {
                throw new CoreException(CoreController.ERR_MSG_403);
            }
        }
    }

}