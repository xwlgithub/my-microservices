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
       List<Role> roleList= roleService.findAll(current,size);
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
     * 解决options
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.OPTIONS)
    public Boolean add(){
       return true;
    }
}
