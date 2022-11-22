package com.hbx.ads.shiro;


import com.hbx.ads.domain.ActiveUser;
import com.hbx.ads.domain.SysUser;
import com.hbx.ads.mapper.SysUserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomRealm extends AuthorizingRealm {


    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public void setName(String name) {
        super.setName("customRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();


        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();


        simpleAuthorizationInfo.addRole(activeUser.getRole());

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();

        SysUser sysUser = null;
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username",username);


        try{
            List<SysUser> sysUsers = sysUserMapper.selectByMap(userMap);
            sysUser = sysUsers.get(0);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        if (sysUser == null){
            if (logger.isDebugEnabled()){
                logger.debug("user not exist");
            }
            return null;
        }

        String password = sysUser.getPassword();
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUserid(sysUser.getId());
        activeUser.setUsername(sysUser.getUsername());
        activeUser.setRole(sysUser.getRole());
        activeUser.setRealName(sysUser.getRealName());
        activeUser.setUserStatus(sysUser.getLocked());


        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password , this.getName() );


        return simpleAuthenticationInfo;
    }
}
