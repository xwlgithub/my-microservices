package com.itxwl.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class TestMyRealm {

    @Test
    public void test(){
        MyRealm myRealm=new MyRealm();
        //构建secrityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealm);
        //shiro加密
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        myRealm.setCredentialsMatcher(matcher);
        //主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("mark","123");
        subject.login(token);
        System.out.println("是否成功"+subject.isAuthenticated());
        subject.checkRoles("admin");
        subject.checkPermissions("user:delete","user:select");
    }
}
