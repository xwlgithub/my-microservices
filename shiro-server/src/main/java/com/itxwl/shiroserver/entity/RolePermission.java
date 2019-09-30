package com.itxwl.shiroserver.entity;

import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限
 */
@Data
@Entity
@Table(name = "roles_permissions")
public class RolePermission  {
    @Id
    private Long id;
    private String roleName;
    private String permission;
    private Long roleId;
    private String permissionShow;

}
