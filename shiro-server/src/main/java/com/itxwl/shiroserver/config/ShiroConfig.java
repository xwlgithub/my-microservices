package com.itxwl.shiroserver.config;

import com.itxwl.shiroserver.realm.CustomRealm;
//import com.itxwl.shiroserver.session.CustomerSessionManager;
import com.itxwl.shiroserver.session.CustomerSessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
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
        filterChainMap.put("/loginFrom", "anon");
        filterChainMap.put("/swagger-ui.html", "anon");
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/success", "anon");
        filterChainMap.put("/LoginJwtProce", "anon");
        filterChainMap.put("/getJwtWithHave", "anon");
        filterChainMap.put("/delete", "anon");
        filterChainMap.put("/webjars/**", "anon");
        filterChainMap.put("/swagger-resources/**", "anon");
        /**
         * 添加授权过滤器 验证当前用户是否有删除权限,如果没有跳转未授权页面或抛出异常信息
         */
   //     filterChainMap.put("/delete", "perms[1]");
        filterChainMap.put("/**", "authc");
        //拦截所有请求直接到登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        //对某些资源进行拦截处理
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;

    /**
     * redis控制器 操作redis
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        return redisManager;
    }

    /**
     * sessionDAO
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 会话管理器
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        CustomerSessionManager sessionManager = new CustomerSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
    /**
     * 缓存管理器
     */
    @Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager=new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
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
        securityManager.setSessionManager(sessionManager());
        //将redis自定义缓存管理器注入环境中
        securityManager.setCacheManager(cacheManager());

        //shiro加密~在前端传入密码进后端后对密码进行加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        customRealm.setCredentialsMatcher(matcher);
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
