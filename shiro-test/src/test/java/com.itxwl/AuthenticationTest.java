package com.itxwl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
@Before
public void  addUser(){
    simpleAccountRealm.addAccount("mark","123","admin","user");
}
    @Test
    public void testAuthentication(){
       //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //主体认证提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("mark","123");
        subject.login(token);
        System.out.println("是否成功"+subject.isAuthenticated());
        //判断当前主体是否满足角色权限
        subject.checkRoles("admin","user");

    }
}
