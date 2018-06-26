package com.ht.vis;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.ehcache.CacheKit;
import com.ht.vis.core.CoreController;
import com.ht.vis.interceptors.AdminAAuthInterceptor;
import com.ht.vis.interceptors.AdminIAuthInterceptor;
import com.ht.vis.kits.ResKit;

@Clear(AdminIAuthInterceptor.class)
public class IndexCtr extends CoreController{
    public void index(){
        String domain= ResKit.getConfig("domain");
        redirect(domain+"/ad/index.html");
    }


}