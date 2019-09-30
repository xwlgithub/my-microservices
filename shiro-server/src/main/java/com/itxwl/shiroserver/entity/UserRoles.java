package com.itxwl.shiroserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 */
@Data
@Entity
@Table(name = "user_roles")
public class UserRoles {
    @Id
    private Long id;
    private String username;
    private String roleName;
    private Long userId;
    private Long permonId;
    private String roleShow;
}
