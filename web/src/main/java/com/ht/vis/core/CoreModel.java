package com.ht.vis.core;

import cn.hutool.core.util.ArrayUtil;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.*;
import com.ht.vis.Consts;
import com.ht.vis.kits.DateKit;
import org.jsoup.Jsoup;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.Executors;

/**
 *
 */
public abstract class CoreModel<M extends CoreModel<M>> extends Model<M> {

	/**
	 * 防止xss攻击处理
	 * 
	 * @return
	 */

	public boolean xSSsave() {
		for (Map.Entry me : _getAttrs().entrySet()) {
			if (me.getValue() instanceof String) {
				set((String) me.getKey(), Jsoup.clean((String) me.getValue(), Consts.basicWithImages));
			}
		}
		return save();
	}
	@Override
	public boolean save() {
		try {
            set("cAt", new Date());
        }catch (Exception e){
            LogKit.error("缺少cAt字段");
        }
		return super.save();
	}

	/**
	 * 防止xss攻击处理
	 * 
	 * @return
	 */
	public boolean xSSupdate() {
		for (Map.Entry me : _getAttrs().entrySet()) {
			if (me.getValue() instanceof String)
				set((String) me.getKey(), Jsoup.clean((String) me.getValue(), Consts.basicWithImages));
		}
		return update();
	}
	@Override
	public boolean update() {

		try {
			set("lAt",new Date());
		}catch (Exception e){
		    LogKit.error("缺少lAt字段");
        }
		return super.update();
	}

	public boolean apDel(){
		try {
            set("dAt", new Date());
        }catch (Exception e){
            LogKit.error("缺少dAt字段");
        }
		return super.update();
	}


	public String getYOrNStr(boolean val) {
		return (val) ? Consts.YORN.yes.getLabel() : Consts.YORN.no.getLabel();
	}

	public String getStatusStr(String val) {
		if (val == null)
			return "";
		return (val.equals(Consts.STATUS.enable.getVal()) ? Consts.STATUS.enable.getValTxt()
				: Consts.STATUS.forbidden.getValTxt());
	}

	public List<M> findByPropEQ(String name, Object val){
		return super.find("select * from "+getTableName()+" where "+name+"=?",val);
	}
	public List<M> findByPropEQWithDat(String name, Object val){
		return super.find("select * from "+getTableName()+" where "+name+"=? and dAt is null",val);
	}
	public M findFirstByPropEQ(String name,Object val){
		return super.findFirst("select * from "+getTableName()+" where "+name+"=?",val);
	}
	public List<M> findByPropEQAndIdNEQ(String name, Object val,Object id){
		return super.find("select * from "+getTableName()+" where "+name+"=? and id!=?",val,id);
	}
	public List<M> findByPropEQAndIdNEQWithDat(String name, Object val,Object id){
		return super.find("select * from "+getTableName()+" where "+name+"=? and id!=? and dAt is null",val,id);
	}


	public List<M> findByPropLIKE(String name, String val){
		return super.find("select * from "+getTableName()+" where "+name+" like ?","%"+val+"%");
	}

	public List<M> findByAndCond( Map<String,Object> cond){
		SqlPara sqlPara=Db.getSqlPara("queryByAndCond",cond);
		return super.find(sqlPara);
	}

	public M findFirstByAndCond( Map<String,Object> cond){
		SqlPara sqlPara=Db.getSqlPara("queryFirstByAndCond",cond);
		return super.findFirst(sqlPara);
	}

	public Page<M> pageByAndCond( Map<String,Object> cond,int pn,int ps){
		SqlPara sqlPara=Db.getSqlPara("queryByAndCond",cond);
		return super.paginate(pn,ps,sqlPara);
	}



	public String getTableName(){
		return TableMapping.me().getTable(getClass()).getName();
	};


	public static Kv buildParamMap(Class clz,Kv cond){
		Kv kv=Kv.by("table",TableMapping.me().getTable(clz).getName());
		kv.put("cond",cond);
		return kv;
	}

	public String getLAtStr(){
		String[] ans=_getAttrNames();
		if(ArrayUtil.contains(ans,"lAt")){
			Date lAt=getDate("lAt");
			if(lAt!=null){
				return DateKit.dateToStr(lAt,DateKit.STR_DATEFORMATE);
			}
		}
		return null;
	}

	public String getCAtStr(){
		String[] ans=_getAttrNames();
		if(ArrayUtil.contains(ans,"cAt")){
			Date cAt=getDate("cAt");
			if(cAt!=null){
				return DateKit.dateToStr(cAt,DateKit.STR_DATEFORMATE);
			}
		}
		return null;
	}

	public String getDAtStr(){
		String[] ans=_getAttrNames();
		if(ArrayUtil.contains(ans,"dAt")){
			Date dAt=getDate("dAt");
			if(dAt!=null){
				return DateKit.dateToStr(dAt,DateKit.STR_DATEFORMATE);
			}
		}
		return null;
	}

}