package com.itxwl.shiroserver.dto;

import com.itxwl.shiroserver.entiry.Permission;
import lombok.Data;

import java.util.List;
@Data
public class PermonDto {
private String permonName;
private List<Permission> permissionList;
}
