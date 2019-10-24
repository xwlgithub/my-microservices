package com.itxwl.shiroserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
/**
 * 菜单编码及菜单名称
 */
@Data
public class PermisssCode {
    @JsonIgnore
    private String id;
    //权限菜单编码
    private String peCode;
    //权限菜单名称
    private String peName;
}
