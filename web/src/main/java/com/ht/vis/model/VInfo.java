package com.ht.vis.model;
import cn.hutool.core.util.StrUtil;
import com.ht.vis.kits.DateKit;
import com.ht.vis.model.base.BaseVInfo;
/**
 * Generated by ap-jf.
 */
@SuppressWarnings("serial")
public class VInfo extends BaseVInfo<VInfo> {
	public static final VInfo dao = new VInfo().dao();
            public String getTypeStr(){
                                    if("0".equals(getType()))
                                        return "公交";
                                    if("1".equals(getType()))
                                        return "校车";
                    return null;
            }
            public String getLineStr(){

                    return null;
            }
            public String getAreaStr(){

                    return null;
            }

}