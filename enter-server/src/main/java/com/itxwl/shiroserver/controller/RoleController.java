package com.itxwl.shiroserver.controller;

import com.itxwl.shiroserver.entiry.Role;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.OPTIONS;
import java.util.Map;

@RestController
@RequestMapping("role")
@CrossOrigin
public class RoleController {

    @PostMapping("add")
    public Map<String,Object> addRole(@RequestBody Role role){
        System.out.println(role.toString());
        return null;
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
