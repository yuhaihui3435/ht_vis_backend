package com.ht.vis.controller.user;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ht.vis.model.CInfo;
import com.ht.vis.query.CInfoQuery;
import com.ht.vis.service.company.CInfoService;
import com.jfinal.aop.Clear;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;
import com.qiniu.common.QiniuException;
import com.ht.vis.Consts;
import com.ht.vis.interceptors.AdminAAuthInterceptor;
import com.ht.vis.kits.DateKit;
import com.ht.vis.kits.QiNiuKit;
import com.ht.vis.kits._StrKit;
import com.ht.vis.query.UserQuery;
import com.ht.vis.kits.ext.BCrypt;
import com.ht.vis.model.User;
import com.ht.vis.validator.user.UserValidator;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.aop.Before;
import com.ht.vis.core.CoreController;
import com.ht.vis.service.user.UserService;
import com.jfinal.aop.Duang;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Clear(AdminAAuthInterceptor.class)
public class UserController extends CoreController{

    private UserService userService=Duang.duang(UserService.class.getSimpleName(),UserService.class);
    private CInfoService cInfoService=Duang.duang(CInfoService.class.getSimpleName(),CInfoService.class);

    public void list(){
        UserQuery userQuery=(UserQuery)getQueryModel(UserQuery.class);
        List<User> ret=userService.findAll(userQuery);
        renderJson(ret);
    }

    public void page(){
        UserQuery userQuery=(UserQuery)getQueryModel(UserQuery.class);
        Page<User> ret=userService.findPage(userQuery);
        renderJson(ret);
    }
    @Before({UserValidator.class})
    public void save(){
        User user=getApModel(User.class);
        if(currUser()!=null){user.setOpId(currUser().getId());}
        user.setStatus(Consts.STATUS.enable.getVal());
        String orgPwd=StrUtil.sub(user.getPhone(),user.getPhone().length()-6,user.getPhone().length());
        user.setSalt(BCrypt.gensalt());
        user.setPassword(BCrypt.hashpw(orgPwd, user.getSalt()));
        userService.save(user);
        renderSuccessJSON("用户信息新增成功");
    }
    @Before({UserValidator.class})
    public void update() throws IOException {
        User user=getApModel(User.class);
        if(StrUtil.isNotBlank(user.getAvatar())){
            if(!user.getAvatar().startsWith("http")) {
                String ret = QiNiuKit.put64imageByProject(user.getAvatar());
                if (!ret.equals(Consts.YORN_STR.no.name()))
                    user.setAvatar(ret);
            }
        }
        if(currUser()!=null){user.setOpId(currUser().getId());}
        userService.update(user);
        renderSuccessJSON("用户信息修改成功");
    }

    //逻辑删除
    @Before({UserValidator.class})
    public void logicDel(){
        Integer[] ids=getParaValuesToInt("ids");
        userService.batchLogicDel(ids,currUser()==null?null:currUser().getId());
        renderSuccessJSON("用户信息删除成功");
    }

    //真实删除
    @Before({UserValidator.class})
    public void del(){
        Integer[] ids=getParaValuesToInt("ids");
        userService.batchDel(ids);
        renderSuccessJSON("用户信息删除成功");
    }
    public void get(){
        Integer id=getParaToInt("id");
        renderJson(userService.findByIdInCache(id));
    }

    /**
     * 设置角色
     */
    public void saveUserRoles(){
        String loginname=getPara("loginname");
        String userRoleCodes=getPara("userRoleCodes");
        String[] userRoleCodesArray=null;
        if(StrUtil.isNotBlank(userRoleCodes))userRoleCodesArray=userRoleCodes.split(",");
        userService.saveUserRoles(loginname,userRoleCodesArray);
        renderSuccessJSON("设置角色成功");
    }

    public void getCurrUser(){
        renderJson(currUser());
    }

    public void updatePwd(){
        String oldPwd=getPara("oldPassword");
        String newPwd=getPara("newPassword");


        if(StrUtil.isBlank(oldPwd)){
            renderFailJSON("旧密码必填");
            return;
        }
        if(StrUtil.isBlank(newPwd)){
            renderFailJSON("新密码必填");
            return;
        }

        User user=currUser();
        user=userService.findOne(user.getId());
        if(BCrypt.checkpw(oldPwd, user.getPassword())){
            user.setPassword(BCrypt.hashpw(newPwd, user.getSalt()));
            userService.update(user);
        }else{
            renderFailJSON("旧密码不正确");
            return;
        }

        renderSuccessJSON("密码修改成功");

    }

    public void resetPwd(){
        Integer id=getParaToInt("userId");
        User user=userService.findOne(id);
        String newPwd=RandomUtil.randomString(6);
        user.setPassword(BCrypt.hashpw(newPwd,user.getSalt()));
        userService.update(user);
        renderSuccessJSON("重置密码成功",newPwd);
    }
    public void init(){
        Map<String,Object> ret=new HashMap<>();
        ret.put("cInfoList",cInfoService.findAll(new CInfoQuery()));
        renderJson(ret);
    }

}