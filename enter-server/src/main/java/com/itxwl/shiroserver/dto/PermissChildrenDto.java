package com.itxwl.shiroserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class PermissChildrenDto {
    private String id;
    private String label;
    private List<PointDto> children;
}
