package com.ht.vis.interceptors;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.ht.vis.Consts;
import com.ht.vis.model.User;
import com.ht.vis.core.CoreController;
import com.ht.vis.core.CoreException;
import com.ht.vis.kits.CookieKit;
import com.ht.vis.kits.ReqKit;
import com.ht.vis.kits.ResKit;
import com.ht.vis.service.user.UserService;

import java.math.BigInteger;

/**
 *
 * 用户身份认证，前置处理
 *
 */
public class AdminIAuthInterceptor implements Interceptor {

	private UserService userService= Duang.duang(UserService.class.getSimpleName(),UserService.class);

	public void intercept(Invocation inv) {
		CoreController controller = (CoreController) inv.getController();
		String userId = CookieKit.get(controller, Consts.USER_ACCESS_TOKEN);
		boolean flag = false;
		User user = null;
		if (StrUtil.isNotEmpty(userId)) {
			flag = true;
			user = userService.findByIdInCache(new Integer(userId));
			if (user == null) {
				if (ReqKit.isAjaxRequest(controller.getRequest())) {
					controller.renderUnauthenticationJSON("sm");

				} else {
					throw new CoreException("你的账户被停用");
				}
			}
		}

		if (flag) {

			String reqCookieVal=controller.getCookie(Consts.USER_ACCESS_TOKEN);
			String currCookieVal=CacheKit.get(Consts.CURR_USER_COOKIE,"user_"+userId);
			if(reqCookieVal.equals(currCookieVal)){
				inv.invoke();
			}else{
				if (ReqKit.isAjaxRequest(controller.getRequest())) {
					controller.renderAuth900("sm");
				} else {
					throw new CoreException("该账户正在其他地方被登录使用");
				}
			}
		} else {
			CookieKit.remove(controller, Consts.USER_ACCESS_TOKEN);
			if (ReqKit.isAjaxRequest(controller.getRequest())) {
				controller.renderUnauthenticationJSON("sm");
			} else {
				throw new CoreException("身份认证失败，请登录！");
			}
		}
	}
}