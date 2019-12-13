package com.itxwl.shiroserver.controller;

import com.itxwl.shiroserver.dto.PPPDto;
import com.itxwl.shiroserver.dto.PermissChildrenDto;
import com.itxwl.shiroserver.dto.PermissDto;
import com.itxwl.shiroserver.dto.PermonDto;
import com.itxwl.shiroserver.entiry.Permission;
import com.itxwl.shiroserver.entiry.PermissionApi;
import com.itxwl.shiroserver.entiry.PermissionPoint;
import com.itxwl.shiroserver.service.PermissionService;
import com.itxwl.shiroserver.utils.IdWorker;
import com.itxwl.shiroserver.utils.PageUtil;
import com.itxwl.shiroserver.utils.R;
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
     * 树形菜单封装
     * @return
     */
    @GetMapping("/findAllPerions")
    public R findAllPerions(){
        List<PermissDto> pageUtil= permissionService.findAllPerions();
        return R.data(pageUtil);
    }

    /**
     * 根据角色获取所属权限信息
     * @return
     */
    @GetMapping("/findRoleByPersId")
    public R findRoleByPersId(@RequestParam("roleId") String roleId){
        List<PPPDto> pageUtil=permissionService.findRoleByPersId(roleId);
        return R.data(pageUtil);
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
