package com.itxwl;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {
    DruidDataSource dataSource=new DruidDataSource();
    {
        //microservices
        dataSource.setUrl("jdbc:mysql://localhost:3306/microservices?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }
    @Test
    public void JdbcRealm(){
        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //查询权限数据时需要开通查询权限
        jdbcRealm.setPermissionsLookupEnabled(true);
        String sql ="select password from user where name = ?";
        String rolesql ="select role_name from roles where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);
        jdbcRealm.setUserRolesQuery(rolesql);
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        //主体认证提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("mark","123");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
//        subject.checkRole("admin");
//        //判断是否包含角色
//        subject.checkRoles("admin","user");
//        subject.checkPermission("user:select");
    }
}
