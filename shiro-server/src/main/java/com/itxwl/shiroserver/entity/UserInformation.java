package com.itxwl.shiroserver.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserInformation {
    private Long userId;
    private String userName;
    private List<UserRoles> userRoles;
    private List<RolePermission> permissions;
}
