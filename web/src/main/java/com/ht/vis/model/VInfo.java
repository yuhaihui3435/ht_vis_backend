package com.ht.vis.model;

import com.ht.vis.Consts;
import com.ht.vis.model.base.BaseVInfo;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Generated by ap-jf.
 */
@SuppressWarnings("serial")
public class VInfo extends BaseVInfo<VInfo> {
    public static final VInfo dao = new VInfo().dao();

    public String getTypeStr() {
        Dd dd=CacheKit.get(Consts.CACHE_NAMES.dd.name(), "id_" + getType().intValue());
        return dd==null?Consts.NON_SET:dd.getName();
    }

    public String getLineStr() {
        VLine vLine=VLine.dao.findFirstByPropEQ("cCode",getCCode());
        return vLine==null?Consts.NON_SET:vLine.getName();
    }

    public String getAreaStr() {
        return null;
    }

    public CInfo getCInfo(){
        return CInfo.dao.findByCodeInCache(getCCode());
    }

}
