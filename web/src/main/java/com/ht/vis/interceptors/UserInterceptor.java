package com.ht.vis.interceptors;


import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.ht.vis.Consts;
import com.ht.vis.model.Res;
import com.ht.vis.model.Role;
import com.ht.vis.model.User;
import com.ht.vis.core.CoreController;
import com.ht.vis.kits.CookieKit;
import com.ht.vis.service.role.RoleService;
import com.ht.vis.service.ser.SerService;
import com.ht.vis.service.user.UserService;


import java.math.BigInteger;

public class UserInterceptor implements Interceptor {

    private RoleService roleService= Duang.duang(RoleService.class.getSimpleName(),RoleService.class);
    private SerService serService= Duang.duang(SerService.class.getSimpleName(),SerService.class);
    private UserService userService= Duang.duang(UserService.class.getSimpleName(),UserService.class);

    @Override
    public void intercept(Invocation inv) {
        CoreController controller = (CoreController)inv.getController();
        String userId= CookieKit.get(controller, Consts.USER_ACCESS_TOKEN);
        if(StrUtil.isNotBlank(userId)) {
            User user=userService.findByIdInCache(new Integer(userId));
            controller.setAttr(Consts.CURR_USER, user);
            String[] roleCodes=roleService.findCodesByLoginnameInCache(user.getLoginname());
//            controller.setAttr(Consts.CURR_USER_ROLES, roleService.findByLoginnameInCache(user.getLoginname()));
//            controller.setAttr(Consts.CURR_USER_SERS, serService.findSersByRoleCodesInCache(roleCodes));
        }

        inv.invoke();
    }
}