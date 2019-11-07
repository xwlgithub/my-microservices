package com.itxwl.shiroserver.dto;

import com.itxwl.shiroserver.entiry.Permission;
import lombok.Data;

import java.util.List;
@Data
public class PermissDto {
    private String id;
    private String label;
    private List<PermissChildrenDto> children;
}
