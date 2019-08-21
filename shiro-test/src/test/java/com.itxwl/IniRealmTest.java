package com.itxwl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void testIniRealm(){
        //INI配置文件路径
        IniRealm realm=new IniRealm("classpath:user");
        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        //主体认证提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("mark","123");
        subject.login(token);
        //授权为admin
        subject.checkRole("admin");
        System.out.println(subject.isAuthenticated());
        //验证是否具备其他权限
        subject.checkPermission("user:update");
    }
}
