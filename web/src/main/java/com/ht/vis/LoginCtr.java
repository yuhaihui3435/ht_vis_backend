package com.ht.vis;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Clear;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.render.JsonRender;
import com.ht.vis.Consts;
import com.ht.vis.kits.DateKit;
import com.ht.vis.kits.ReqKit;
import com.ht.vis.model.LogOp;
import com.ht.vis.model.Res;
import com.ht.vis.model.Role;
import com.ht.vis.model.User;
import com.ht.vis.core.CoreController;
import com.ht.vis.interceptors.AdminAAuthInterceptor;
import com.ht.vis.interceptors.AdminIAuthInterceptor;
import com.ht.vis.kits.CookieKit;
import com.ht.vis.kits.ResKit;
import com.ht.vis.kits.ext.BCrypt;
import com.ht.vis.service.res.ResService;
import com.ht.vis.service.role.RoleService;
import com.ht.vis.service.ser.SerService;

import java.util.*;

/**
 *  登录请求 处理
 */
@Clear({AdminIAuthInterceptor.class, AdminAAuthInterceptor.class})
public class LoginCtr extends CoreController {

    public static final int LOGIN_MAX_RETRY_COUNT = 5;//密码重试最大次数
    public static final int LOGIN_PROTECTED_MIN = 15;//登陆失败保护时间
    public static final String LOGIN_RETRY_DATE = "LOGIN_RETRY_DATE";
    public static final String LOGIN_RETRY_COUNT = "LOGIN_RETRY_COUNT";

    private SerService serService=enhance(SerService.class.getSimpleName(),SerService.class);
    private ResService resService=enhance(ResService.class.getSimpleName(),ResService.class);
    private RoleService roleService=enhance(RoleService.class.getSimpleName(),RoleService.class);

    public void login() {
        String loginname = getPara("loginname");
        String password = getPara("password");
        String rm = getPara("rememberMe");
        String from=getPara("from");
        DateTime reTryDate = (DateTime) CacheKit.get(Consts.CACHE_NAMES.login.name(), loginname + "LOGIN_RETRY_DATE");
        if (reTryDate != null) {
            Date now = new Date();
            if (reTryDate.compareTo(now) >= 0) {
                String s = DateUtil.formatBetween(reTryDate, now);
                renderFailJSON("你的账户由于尝试登录失败次数查过5次，暂时被保护。请：" + s + "后重试。");
                return;
            }else{
                CacheKit.remove(Consts.CACHE_NAMES.login.name(),loginname+LOGIN_RETRY_DATE);
            }
        }

        if (StrUtil.isBlank(loginname)) {
            renderFailJSON("用户名不能为空");
            return;
        }

        if (StrUtil.isBlank(password)) {
            renderFailJSON("密码不能为空");
            return;
        }



        String mpId=CacheKit.get(Consts.CACHE_NAMES.paramCache.name(),"mpId");
        String[] mpIds= StrUtil.isNotBlank(mpId)?mpId.split(";"):null;
        if(StrUtil.isNotBlank(from)&&mpIds!=null&& ArrayUtil.contains(mpIds,from)) {
            LogKit.info("通过小程序端登录，不需要使用验证码");
        }else{
            if (ResKit.getConfigBoolean("userAuth")) {
                if (!validateCaptcha("checkCode")) {
                    renderFailJSON("验证码不正确");
                    return;
                }
            }
        }

        User user = User.dao.findFirst("select * from s_user where loginname=? and dAt is null", loginname);

        if (user == null) {
            renderFailJSON("用户不存在!");
            return;
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
            CacheKit.remove(Consts.CACHE_NAMES.login.name(), loginname + "LOGIN_RETRY_DATE");
            CacheKit.remove(Consts.CACHE_NAMES.login.name(), loginname + "LOGIN_RETRY_COUNT");
            if (user.getStatus().equals(Consts.STATUS.enable.getVal())) {
                List<Role> roleList=roleService.findByLoginnameInCache(user.getLoginname());
                String[] roleCodes=new String[roleList.size()];
                for (int i = 0; i < roleList.size(); i++) {
                    roleCodes[i]=roleList.get(i).getCode();
                }
                Map<String, Object> data = new HashMap<String, Object>();
                if(roleCodes!=null&&roleCodes.length>0) {
                    Set<String> resList = resService.findUrlByRoleCodesInCache(roleCodes);
                    data.put("resList", resList);
                    Set<String> serList=serService.findUrlByRoleCodesInCache(roleCodes);
                    data.put("serList", serList);
                }
                Date now=new Date();
                if(user.getLoginTime()!=null)
                    user.setLastLoginTime(user.getLoginTime());
                else
                    user.setLastLoginTime(now);
                if(StrUtil.isNotBlank(user.getLoginIp()))
                    user.setLastLoginIp(user.getLoginIp());
                else
                    user.setLastLoginIp(ReqKit.getRemoteHost(getRequest()));
                user.setLoginTime(now);
                user.setLoginIp(ReqKit.getRemoteHost(getRequest()));
                user.update();
                data.put("lastLoginTime", DateKit.dateToStr(user.getLastLoginTime(),DateKit.STR_DATEFORMATE));
                data.put("lastLoginIp",user.getLastLoginIp());
                data.put("nickname", user.getNickname());
                data.put("loginname", user.getLoginname());
                data.put("avatar",user.getAvatar());
                String cookieVal=null;
                if (StrKit.notBlank(rm) && rm.equals("0"))
                    cookieVal=CookieKit.put(this, Consts.USER_ACCESS_TOKEN, user.getId().toString(), 60 * 60 * 24 * 14);
                else
                    cookieVal=CookieKit.put(this, Consts.USER_ACCESS_TOKEN, user.getId().toString(), Consts.COOKIE_TIMEOUT);


                //登录检查统一用户是否在线，根据不同策略进行处理
                String currUserCookie=(String)CacheKit.get(Consts.CURR_USER_COOKIE,"user_"+user.getId());
                if(StrUtil.isBlank(currUserCookie)) {
                    if (cookieVal != null) {
                        CacheKit.put(Consts.CURR_USER_COOKIE, "user_" + user.getId(), cookieVal);
                    }
                }else{
                    String cookieExpire=CookieKit.getFromCookieInfo(Consts.ENCRYPT_KEY,currUserCookie);
                    if(cookieExpire==null){
                        if (cookieVal != null) {
                            CacheKit.put(Consts.CURR_USER_COOKIE, "user_" + user.getId(), cookieVal);
                        }
                    }else{
                        String userOnlineTactics=CacheKit.get(Consts.CACHE_NAMES.paramCache.name(),"userOnlineTactics");
                        if(StrUtil.isBlank(userOnlineTactics)){
                            userOnlineTactics=Consts.USER_ONLINE_TACTICS_LOGIN_FIRST;
                        }
                        switch (userOnlineTactics){
                            case Consts.USER_ONLINE_TACTICS_LOGIN_FIRST:
                                renderFailJSON("该账户正在被使用，您目前无法再次进行登录");
                                return;
                            case Consts.USER_ONLINE_TACTICS_AFTER_LOGGING_IN:
                                CacheKit.put(Consts.CURR_USER_COOKIE, "user_" + user.getId(), cookieVal);
                                break;
                        }

                    }
                }
                renderSuccessJSON("登录成功", JSON.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
                return;
            } else {
                renderFailJSON("该用户被禁用", "");
                return;
            }
        } else {


            Integer pwdErrCount = (Integer) CacheKit.get(Consts.CACHE_NAMES.login.name(), loginname + "LOGIN_RETRY_COUNT");
            if (pwdErrCount == null) {
                pwdErrCount = 1;
                CacheKit.put(Consts.CACHE_NAMES.login.name(),loginname+LOGIN_RETRY_COUNT,pwdErrCount);
                renderFailJSON("密码不正确,还可以尝试:" + (LOGIN_MAX_RETRY_COUNT - pwdErrCount) + "次");
            } else if (pwdErrCount == LOGIN_MAX_RETRY_COUNT) {
                Date date = new Date();
                DateTime dateTime = DateUtil.offsetMinute(date, LOGIN_PROTECTED_MIN);
                CacheKit.put(Consts.CACHE_NAMES.login.name(), loginname + "LOGIN_RETRY_DATE", dateTime);
                CacheKit.remove(Consts.CACHE_NAMES.login.name(), loginname + "LOGIN_RETRY_COUNT");
                renderFailJSON("您的账号尝试登陆失败次数过多，将被进行保护，请"+LOGIN_PROTECTED_MIN+"分钟后重试");
            } else if (pwdErrCount < LOGIN_MAX_RETRY_COUNT) {

                pwdErrCount++;
                CacheKit.put(Consts.CACHE_NAMES.login.name(),loginname+LOGIN_RETRY_COUNT,pwdErrCount);
                renderFailJSON("密码不正确,还可以尝试:" + (LOGIN_MAX_RETRY_COUNT - pwdErrCount) + "次");
            }


            LogOp logOp = new LogOp();
            logOp.setReqParam(JsonKit.toJson(user));
            logOp.setOpName(loginname);
            logOp.setReqIp(HttpUtil.getClientIP(getRequest()));
            logOp.setReqAt(new Date());
            logOp.setOpChannel("local");
            logOp.setReqMethod(getClass().getCanonicalName() + ".login");
            logOp.setReqRet(((JsonRender) getRender()) != null ? ((JsonRender) getRender()).getJsonText() : "密码不正确");
            logOp.save();
            return;
        }

    }

    public void logout() {
        String userId=CookieKit.get(this,Consts.USER_ACCESS_TOKEN);
        CookieKit.remove(this, Consts.USER_ACCESS_TOKEN);
        CacheKit.remove(Consts.CURR_USER_COOKIE,"user_"+userId);//删除当前在线用户缓存中标识
        renderSuccessJSON("退出系统成功");
    }



    public void createCaptch() {
        renderCaptcha();
    }

}