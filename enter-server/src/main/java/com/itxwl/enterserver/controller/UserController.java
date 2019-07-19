package com.itxwl.enterserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itxwl.enterserver.entiry.User;
import com.itxwl.enterserver.respotitory.UserRepository;
import com.itxwl.enterserver.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
        private UserService userService;
    /**
     * 添加
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    public Map<String,Object> saveUser(@RequestBody String userJson){
        Map<String,Object> map=new HashMap<>();
        Object userObject = JSONObject.parseObject(userJson).get("userJson");
        User user = JSONObject.toJavaObject((JSON) userObject, User.class);
        if (StringUtils.isEmpty(user.toString())){
            map.put("error","传入数据不能为空!");
            return map;
        }
        Boolean isSuccess=false;
        try {
             isSuccess= userService.saveUser(user);
        } catch (Exception e) {
            map.put("error",e.getMessage());
        }
        if (isSuccess==true){
            map.put("success",true);
            return map;
        }
        map.put("error","保存失败!");
        return map;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping(value = "deletes/{id}")
    public Map<String,Object> deletes(@PathVariable("id")Long id){
        Map<String,Object> map =new HashMap<>();
        if (id==null){
            map.put("error","请最少选择一条数据!");
            return map;
        }
        Integer integer=0;
        try {
             integer=  userService.deletes(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (integer>=1 && integer<=100){
            map.put("success",true);
            return map;
        }
        map.put("error","未知错误!");
        return  map;
    }

    @GetMapping(value = "selectAll")
    //Page<List<User>>
    public Object selectAll(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable){
        List<User> userList=null;
        try {
             userList= userService.selectAll();
        } catch (Exception e) {
            Map<String,String> map=new HashMap<>();
            map.put("error","查询失败!");
            return map;
        }
        return userList;
    }

}
