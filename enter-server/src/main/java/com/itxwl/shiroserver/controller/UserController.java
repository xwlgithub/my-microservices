package com.itxwl.shiroserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itxwl.shiroserver.entiry.User;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    /**
     * 查询
     * @param
     * @return
     */
    @GetMapping(value = "/selectAll/{id}")
    public ResponseEntity<User> selectById(@PathVariable("id") Long id){
        User user=null;
        try {
            user= userService.selectById(id);
        } catch (MyException e) {
           throw  new MyException(e.getExceptionEnum());
        }
        return ResponseEntity.ok(user);
    }


}
