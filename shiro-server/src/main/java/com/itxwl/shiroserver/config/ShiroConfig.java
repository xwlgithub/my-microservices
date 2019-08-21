package com.itxwl.shiroserver.config;

import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
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
     * 配置shiro web过滤器, 拦截请求
     */
    @Bean
    public ShiroFilterFactoryBean shiroWebConfigFilter() throws  MyException {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        //添加securityManager环境
        try {
            shiroFilterFactoryBean.setSecurityManager(securityManager());
            Map<String, String> filterChainMap = new LinkedHashMap<String, String>(16);

            //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
            filterChainMap.put("/loginFrom", "anon");
            //filterChainMap.put("/success", "anon");
            filterChainMap.put("/**", "authc");
            //拦截所有请求直接到登录页面
            shiroFilterFactoryBean.setLoginUrl("/login");
            //shiroFilterFactoryBean.setSuccessUrl("/success");
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        } catch (Exception e) {
            throw  new MyException(ExceptionEnum.USERNAME_AND_PSD_ERROR);
        }
        return shiroFilterFactoryBean;
    }

    /**
     * 构建secrityManager环境
     * @return
     */
    @Bean
    public SecurityManager securityManager() throws MyException{
        DefaultWebSecurityManager securityManager= null;
        try {
            securityManager = new DefaultWebSecurityManager();
            //将自定义realm注入环境中去
            CustomRealm customRealm = new CustomRealm();
            securityManager.setRealm(customRealm);
            //shiro加密
            HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
            matcher.setHashAlgorithmName("md5");
            customRealm.setCredentialsMatcher(matcher);
        } catch (MyException e) {
            throw  new MyException(e.getExceptionEnum());
        }
        return securityManager;
    }
}
