package com.itxwl.shiroserver.controller;

import com.itxwl.shiroserver.dto.PermonDto;
import com.itxwl.shiroserver.entiry.Permission;
import com.itxwl.shiroserver.entiry.PermissionApi;
import com.itxwl.shiroserver.entiry.PermissionPoint;
import com.itxwl.shiroserver.service.PermissionService;
import com.itxwl.shiroserver.utils.IdWorker;
import com.itxwl.shiroserver.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/permission")
@RestController
@SuppressWarnings("all")
@AllArgsConstructor
public class PermissionController {
    private PermissionService permissionService;
    /**
     * 查询权限菜单信息
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/findAllPerion")
    public List<PermonDto> findAllPerion(){
        List<PermonDto> pageUtil= permissionService.findAllPerion();
        return pageUtil;
    }

    /**
     * 查看该权限菜单所属按钮列表
     * @return
     */
    @GetMapping("/findPointById/{id}")
    public List<PermissionPoint> findPointById(@PathVariable("id") String id){
        List<PermissionPoint> permissionPoints=permissionService.findPointById(id);
        return permissionPoints;
    }

    @GetMapping("/findApiById/{id}")
    public PermissionApi findApiById(@PathVariable("id")String id){
       return permissionService.findApiById(id);
    }
}
