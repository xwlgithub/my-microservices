package com.itxwl.shiroserver.entity;

import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户
 */
@Entity
@Table(name = "users")
@Data
/**
 * redis 和shiro提供的插件包
 */
public class User   implements AuthCachePrincipal {
    @Id
    private Long id;
    private String username;
    private String password;
    private Long roleId;

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
