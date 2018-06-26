package com.ht.vis.service.user;
import com.jfinal.plugin.ehcache.CacheKit;
import com.ht.vis.Consts;
import com.ht.vis.core.CoreService;
import com.ht.vis.model.User;
import com.ht.vis.model.UserRole;
import com.ht.vis.query.UserQuery;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.kit.Kv;
import java.util.*;
import com.jfinal.plugin.activerecord.Page;

public class UserService extends CoreService{

    private static User userDao=User.dao;

    public List<User> findAll(UserQuery userQuery){
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(userQuery.getLoginname())){
            kv.put("loginname like","%"+userQuery.getLoginname()+"%");
        }
        if(StrUtil.isNotBlank(userQuery.getNickname())){
            kv.put("nickname like","%"+userQuery.getNickname()+"%");
        }
        if(StrUtil.isNotBlank(userQuery.getPhone())){
            kv.put("phone=",userQuery.getPhone());
        }
        if(StrUtil.isNotBlank(userQuery.getEmail())){
            kv.put("email=",userQuery.getEmail());
        }
        if(StrUtil.isNotBlank(userQuery.getStatus())){
            kv.put("status=",userQuery.getStatus());
        }
        if(StrUtil.isNotBlank(userQuery.getBeginCAt())){
            kv.put("cAt >=",userQuery.getBeginCAt());
        }
        if(StrUtil.isNotBlank(userQuery.getEndCAt())){
            kv.put("cAt <=",userQuery.getEndCAt());
        }
        kv.put("dAt","");
        if(StrUtil.isNotBlank(userQuery.getOrderBy())) {
            kv.put("orderBy", userQuery.getOrderBy());
        }
        kv=User.buildParamMap(User.class,kv);
        return userDao.findByAndCond(kv);
    }


    public Page<User> findPage(UserQuery userQuery){
        Kv kv= Kv.create();
        if(StrUtil.isNotBlank(userQuery.getLoginname())){
            kv.put("loginname like","%"+userQuery.getLoginname()+"%");
        }
        if(StrUtil.isNotBlank(userQuery.getNickname())){
            kv.put("nickname like","%"+userQuery.getNickname()+"%");
        }
        if(StrUtil.isNotBlank(userQuery.getPhone())){
            kv.put("phone=",userQuery.getPhone());
        }
        if(StrUtil.isNotBlank(userQuery.getEmail())){
            kv.put("email=",userQuery.getEmail());
        }
        if(StrUtil.isNotBlank(userQuery.getStatus())){
            kv.put("status=",userQuery.getStatus());
        }
        if(StrUtil.isNotBlank(userQuery.getBeginCAt())){
            kv.put("cAt >=",userQuery.getBeginCAt());
        }
        if(StrUtil.isNotBlank(userQuery.getEndCAt())){
            kv.put("cAt <=",userQuery.getEndCAt());
        }
        kv.put("dAt","");
        if(StrUtil.isNotBlank(userQuery.getOrderBy())) {
            kv.put("orderBy", userQuery.getOrderBy());
        }
        kv=User.buildParamMap(User.class,kv);
        return userDao.pageByAndCond(kv,userQuery.getPn(),userQuery.getPs());
    }

    public User findOne(Integer id){
        return userDao.findById(id);
    }
    @Before({Tx.class})
    public void save(User user){
        user.save();
    }
    @Before({Tx.class})
    public void update(User user){
        user.update();
        CacheKit.remove(Consts.CACHE_NAMES.user.name(),"id_"+user.getId());
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findByLoginnameInCache_"+user.getLoginname());
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+user.getLoginname());
    }
    @Before({Tx.class})
    public void logicDel(Integer id,Integer opId){
        User user=findOne(id);

        if(opId!=null){
            user.setOpId(opId);
        }

        user.apDel();
        CacheKit.remove(Consts.CACHE_NAMES.user.name(),"id_"+user.getId());
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findByLoginnameInCache_"+user.getLoginname());
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+user.getLoginname());
    }
    @Before({Tx.class})
    public void del(Integer id){
        User user=findOne(id);
        user.delete();
        CacheKit.remove(Consts.CACHE_NAMES.user.name(),"id_"+user.getId());
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findByLoginnameInCache_"+user.getLoginname());
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+user.getLoginname());
    }
    @Before({Tx.class})
    public void batchLogicDel(Integer[] ids,Integer opId){
        User user=null;
        if(ids!=null){
            for(Integer id:ids){
                user=User.dao.findById(id);
                logicDel(id,opId);
                CacheKit.remove(Consts.CACHE_NAMES.user.name(),"id_"+user.getId());
                CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findByLoginnameInCache_"+user.getLoginname());
                CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+user.getLoginname());
            }
        }
    }
    @Before({Tx.class})
    public void batchDel(Integer[] ids){
        User user=null;
        if(ids!=null){
            for(Integer id:ids){
                user=User.dao.findById(id);
                del(id);
                CacheKit.remove(Consts.CACHE_NAMES.user.name(),"id_"+user.getId());
                CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findByLoginnameInCache_"+user.getLoginname());
                CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+user.getLoginname());
            }
        }
    }
    @Before({Tx.class})
    /**
     *
     * 建立用户角色关系
     *
     */
    public void saveUserRoles(String loginname,String[] resCodes){
        List<UserRole> userRoles=UserRole.dao.find("select * from s_user_role where loginname=?",loginname);
        for(UserRole userRole:userRoles){
            userRole.delete();
        }
        UserRole userRole=null;
        if(resCodes!=null) {
            for (String resCode : resCodes) {
                userRole = new UserRole();
                userRole.setLoginname(loginname);
                userRole.setRoleCode(resCode);
                userRole.save();
            }
        }
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findByLoginnameInCache_"+loginname);
        CacheKit.remove(Consts.CACHE_NAMES.userRoles.name(),"findCodesByLoginnameInCache_"+loginname);

    }

    public User findByIdInCache(Integer userId){
        return User.dao.findFirstByCache(Consts.CACHE_NAMES.user.name(), "id_"+userId, "select id,loginname,nickname,phone,email,avatar,status,lAt,opId,cAt,dAt,lastLoginTime,lastLoginIp from s_user where status='0' and id=? and dAt is null", userId);
    }
}
