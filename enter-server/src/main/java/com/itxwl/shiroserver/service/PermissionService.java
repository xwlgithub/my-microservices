package com.itxwl.shiroserver.service;

import com.itxwl.shiroserver.dto.PermonDto;
import com.itxwl.shiroserver.entiry.Permission;
import com.itxwl.shiroserver.entiry.PermissionApi;
import com.itxwl.shiroserver.entiry.PermissionPoint;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PermissionService {
    //查看权限菜单信息
    List<PermonDto> findAllPerion();
    //查看菜单所属按钮列表
    List<PermissionPoint> findPointById(String id);
    //根据按钮id获取对应API
    PermissionApi findApiById(String id);
}
