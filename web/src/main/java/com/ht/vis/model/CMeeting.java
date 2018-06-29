package com.ht.vis.model;

import cn.hutool.core.util.StrUtil;
import com.ht.vis.Consts;
import com.ht.vis.kits.DateKit;
import com.ht.vis.model.base.BaseCMeeting;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Generated by ap-jf.
 */
@SuppressWarnings("serial")
public class CMeeting extends BaseCMeeting<CMeeting> {
    public static final CMeeting dao = new CMeeting().dao();

    public String getHostStr() {

        return null;
    }

    public String getTypeStr() {
        Dd dd=CacheKit.get(Consts.CACHE_NAMES.dd.name(),"id_"+getType());
        return dd.getName();
    }

}
