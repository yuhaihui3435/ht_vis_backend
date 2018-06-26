package com.ht.vis.core;

import cn.hutool.log.StaticLog;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.ehcache.CacheKit;
import com.ht.vis.Consts;
import com.ht.vis.model.Dd;
import com.ht.vis.model.Param;
import com.ht.vis.model.Ser;
import com.ht.vis.query.SerQuery;
import com.ht.vis.service.ser.SerService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class CoreData {

	private static SerService serService= Duang.duang(SerService.class.getSimpleName(),SerService.class);

	public static void loadAllCache() {
		loadParam();
		loadDd();
		loadAllSer();
	}
	//数据系统参数
	public static void loadParam() {
		List<Param> list = Param.dao.find("select * from " + Param.TABLE);
		CacheKit.removeAll(Consts.CACHE_NAMES.paramCache.name());
        for (Param p : list) {

            // RenderManager.me().getEngine().getEngineConfig().addSharedObject(p.getK(),p.getVal());
            CacheKit.put(Consts.CACHE_NAMES.paramCache.name(), p.getK(), p.getVal());
        }
        StaticLog.info("系统参数加载成功");
	}
	//读取数据字典
	public static void loadDd() {
		CacheKit.removeAll(Consts.CACHE_NAMES.dd.name());
		List<Dd> list = Dd.dao.findParentAll();
		List<Dd> list1 = null;
		for (Dd dd : list) {
			list1 = dd.getChildren();
			CacheKit.put(Consts.CACHE_NAMES.dd.name(), dd.getDict().concat("List"), list1);
			for (Dd dd1 : list1) {
				CacheKit.put(Consts.CACHE_NAMES.dd.name(), "id_"+dd.getId(), dd);
			}
		}
	}

	public static void loadAllSer(){
		CacheKit.removeAll(Consts.CACHE_NAMES.allSer.name());
		List<Ser> list=serService.findAll(new SerQuery());
		Set<String> strings=new HashSet<>();
		for (Ser ser:list){
			strings.add(ser.getUrl());
		}
		CacheKit.put(Consts.CACHE_NAMES.allSer.name(),"allSer",strings);
	}
}