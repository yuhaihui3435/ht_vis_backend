package com.ht.vis.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.jfinal.core.Controller;
import com.ht.vis.Consts;
import com.ht.vis.model.Role;
import com.ht.vis.model.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CoreController extends Controller {
    public static final String PAGENUMBER = "pn";
    public static final String PAGESIZE = "ps";
    public static final String ORDER="descending";
    public static final String SORTBY="sortBy";
    public static final String ERROR_MSG="_err_msg";
    public static final String SUCCESS_MSG="_suc_msg";


    /**
     * @param @return 参数说明
     * @return int    返回类型
     * @throws
     * @Title: getPN
     * @Description: 获取当前第几页
     */
    public int getPN() {
        int pagenumber = getParaToInt(PAGENUMBER, 0);

        pagenumber = (pagenumber == 0) ? 1 : pagenumber;
        return pagenumber;
    }

    /**
     * @param @return 参数说明
     * @return int    返回类型
     * @throws
     * @Title: getPS
     * @Description: 每页显示多少条，如果页面上没有设置默认显示15条。
     */
    public int getPS() {
        return getParaToInt(PAGESIZE, 15);
    }

    public String orderBy(){

        String order=isParaBlank(ORDER)?null:getPara(ORDER);
        String sortBy=isParaBlank(SORTBY)?null:getPara(SORTBY);

        if(StrUtil.isNotBlank(order)&&StrUtil.isNotBlank(sortBy))
            return sortBy+" "+(("true".equals(order))?"desc":"asc");
        else
            return null;
    }

    /**
     *
     * @param data 数据 参数数组 可变参数，第一位 返回消息，第二位返回的数据
     * @return void
     * @throws
     * @author: 于海慧  2016/12/4
     * @Description: 请求成功的JSON返回结果
     **/
    public void renderSuccessJSON(String ...data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resCode", Consts.REQ_JSON_CODE.success.name());
        jsonObject.put("resMsg", data.length==0  ? "操作成功" : data[0]);
        jsonObject.put("resData", data.length>=2?data[1]:"");
        renderJson(jsonObject.toJSONString());
    }

    /**
     * @param data 数据   可变参数，第一位 返回消息，第二位返回的数据
     * @return void
     * @throws
     * @author: 于海慧  2016/12/4
     * @Description: 请求失败的JSON返回结果
     **/
    public void renderFailJSON(String ...data ) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resCode", Consts.REQ_JSON_CODE.fail.name());
        jsonObject.put("resMsg", data.length==0 ? "操作失败" : data[0]);
        jsonObject.put("resData", data.length>=2?data[1]:"");
        renderJson(jsonObject.toJSONString());
    }

    /**
     * @param
     * @return void
     * @throws
     * @author: 于海慧  2017/1/16
     * @Description:用户未通过认证返回结果
     **/
    public void renderUnauthenticationJSON(String str) {
        getResponse().setStatus(401);
        getResponse().setHeader("customData",str);
        renderFailJSON("用户身份无效或被禁用，再进行操作。");
        return;
    }



    public void renderUnauthorizationJSON(String str){
        getResponse().setStatus(403);
        getResponse().setHeader("customData",str);
        renderFailJSON("您没有权限访问该资源。");
        return;
    }

    public void renderAuth900(String str) {
        getResponse().setStatus(900);
        getResponse().setHeader("customData",str);
        renderFailJSON("您的账户正在其他地方进行登录");
        return;
    }


    private static  ValueFilter filter = new ValueFilter() {
        public Object process(Object obj, String s, Object v) {
            if(v==null)
                return "";
            return v;
        }
    };

    protected User currUser(){
        return (User)getAttr(Consts.CURR_USER);
    }

//    protected List<Role> currUserRoles(){return getAttr(Consts.CURR_USER_ROLES);}

    protected <T> T getApModel(Class clz){
        return (T)getModel(clz,"",true);
    }
    protected Object getQueryModel(Class clz) {
        Object bean= null;
        try {
            bean = clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] fields=ReflectUtil.getFieldsDirectly(clz,true);
        Map paraMap=getParaMap();
        Iterator it=paraMap.entrySet().iterator();
        String name;
        Object val;
        String[] vals;
        A:while (it.hasNext()){
            Map.Entry entry=(Map.Entry)it.next();
            name=(String) entry.getKey();
            vals=(String[])entry.getValue();
            if(vals.length==1)val=vals[0];
            else val=ArrayUtil.join(vals,StrUtil.COMMA);
            for(Field f:fields){
                if(f.getName().equals(name)){
                    String typeName=f.getType().getName();
                    if(typeName.equals("java.lang.Long")||typeName.equals("long")){
                        val=Long.parseLong((String)val);
                    }else if(typeName.equals("java.lang.Integer")||typeName.equals("int")){
                        val=Integer.parseInt((String)val);
                    }else if(typeName.equals("java.lang.String")){
                        val=(String)val;
                    }else if(typeName.equals("java.util.Date")){
                        val= DateUtil.parse((String)val);
                    }else if(typeName.equals("java.lang.Double")){
                        val= Double.parseDouble((String)val);
                    }else if(typeName.equals("java.math.BigDecimal")){
                        val= new BigDecimal((String)val);
                    }
                    ReflectUtil.setFieldValue(bean,f,val);
                    continue A;
                }
            }
        }
        return bean;
    }

}