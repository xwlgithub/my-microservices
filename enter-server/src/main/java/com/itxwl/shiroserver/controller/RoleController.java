package com.itxwl.shiroserver.controller;

import com.itxwl.shiroserver.entiry.Role;
import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.service.RoleService;
import com.itxwl.shiroserver.utils.PageUtil;
import com.itxwl.shiroserver.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.OPTIONS;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("role")
@CrossOrigin
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    /**
     * 添加
     * @param role
     * @return
     */
    @PostMapping("add")
    public Map<String,Object> addRole(@RequestBody Role role){
        Map<String,Object> map=new HashMap<>();
        System.out.println(role.toString());
       Boolean bn= roleService.add(role);
       if (bn){
           map.put("success",true);
       }else {
           map.put("error",false);
       }
        return map;
    }

    /**
     * 查询所有
     * @param current
     * @param size
     * @return
     */
    @GetMapping("findAll")
    public PageUtil<Role> findAll(Integer current,Integer size){
       List<Role> roleList= roleService.findAll();
       PageUtil<Role> pageUtil=new PageUtil<Role>(current,size,roleList);
       return pageUtil;
    }

    /**
     * 根据用户id获取所属角色信息
     * @param userId
     * @return
     */
    @GetMapping(value = "findRolesByUserId")
    public R findRolesByUserId(String userId){
        List<String> list=null;
        try {
            list= roleService.findRolesByUserId(userId);
        } catch (MyException e) {
           return new R(false,e.getExceptionEnum().getMessage());
        }
        return new R(true,list);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "deleteRoleById/{id}")
    public Map<String,Object> deleteRoleById(@PathVariable("id") String id){
        Map<String,Object> map=new HashMap<>();
        Boolean isSuccess=false;
        try {
             isSuccess=roleService.deleteRoleById(id);
        } catch (MyException e) {
            map.put("success",e.getExceptionEnum().getMessage());
           return map;
        }
        if (isSuccess) {
            map.put("success", true);
        }
        return map;
    }

    /**
     * 分配权限
     * @param roleId
     * @param permissIds
     * @return
     */
    @PostMapping(value = "authonRoleById")
    public Map<String,Object> authonRoleById(@RequestParam("roleId") String roleId,@RequestParam("permissIds")String permissIds,@RequestParam("myDeleteds")String myDeleteds){
        Map<String,Object> map=new HashMap<>();
        Boolean isSuccess=false;
        try {
            isSuccess= roleService.authonRoleById(roleId,permissIds,myDeleteds);
        } catch (MyException e) {
            map.put("success",e.getExceptionEnum().getMessage());
            return map;
        }
        if (isSuccess) {
            map.put("success", true);
        }
        return map;
    }


    @RequestMapping(value = "deleteRoleById",method = RequestMethod.OPTIONS)
    public boolean deleteRoleById(){
        return true;
    }
    /**
     * 解决options
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.OPTIONS)
    public Boolean add(){
       return true;
    }
}
