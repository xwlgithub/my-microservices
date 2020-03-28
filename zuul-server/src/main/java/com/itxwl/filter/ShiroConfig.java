package com.itxwl.filter;

import com.itxwl.realm.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@SuppressWarnings("all")
public class ShiroConfig  {
    /**
     * 配置shiro web过滤器, 拦截/转发请求
     * 过滤器工厂
     */
    @Bean
    public ShiroFilterFactoryBean shiroWebConfigFilter() {
        System.out.println("运行配置shiro web过滤器, 拦截/转发请求");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置拦截链 LinkedHashMap有序，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        //添加securityManager环境
        //将shiro环境添加过滤器链~
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        Map<String, String> filterChainMap = new LinkedHashMap<>();

        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
        filterChainMap.put("/auth/loginFrom", "anon");
        /**
         * 添加授权过滤器 验证当前用户是否有删除权限,如果没有跳转未授权页面或抛出异常信息
         */
   //     filterChainMap.put("/delete", "perms[1]");
        filterChainMap.put("/**", "authc");
        //拦截所有请求直接到登录页面
        //shiroFilterFactoryBean.setLoginUrl("/login");
        //shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        //对某些资源进行拦截处理
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 构建secrityManager环境
     * 创建安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        System.out.println("进入securityManager环境");
        //web应用使用它进行构建securityManager环境
        DefaultWebSecurityManager securityManager = null;
        securityManager = new DefaultWebSecurityManager();
        //将自定义realm注入环境中去
        CustomRealm customRealm = new CustomRealm();
        securityManager.setRealm(customRealm);
        //将自定义的session会话管理添加进去
       // securityManager.setSessionManager(sessionManager());
        //将redis自定义缓存管理器注入环境中
       // securityManager.setCacheManager(cacheManager());

        //shiro加密~在前端传入密码进后端后对密码进行加密
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        matcher.setHashAlgorithmName("md5");
//        customRealm.setCredentialsMatcher(matcher);
        return securityManager;
    }
    //4.配置shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
