package com.itxwl.shiroserver.config;

import com.itxwl.shiroserver.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 配置shiro web过滤器, 拦截/转发请求
     */
    @Bean
    public ShiroFilterFactoryBean shiroWebConfigFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置拦截链 LinkedHashMap有序，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        //添加securityManager环境
        //将shiro环境添加过滤器链~
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        Map<String, String> filterChainMap = new LinkedHashMap<>();

        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
        filterChainMap.put("/loginFrom", "anon");
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/success", "anon");
        /**
         * 添加授权过滤器 验证当前用户是否有删除权限,如果没有跳转未授权页面或抛出异常信息
         */
        filterChainMap.put("/delete", "perms[select]");
        filterChainMap.put("/**", "authc");
        //拦截所有请求直接到登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        //对某些资源进行拦截处理
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 构建secrityManager环境
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = null;
        securityManager = new DefaultWebSecurityManager();
        //将自定义realm注入环境中去
        CustomRealm customRealm = new CustomRealm();
        securityManager.setRealm(customRealm);
        //shiro加密~在前端传入密码进后端后对密码进行加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        customRealm.setCredentialsMatcher(matcher);
        return securityManager;
    }
}
