package com.itxwl.shiroserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itxwl.shiroserver.entiry.User;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.service.UserService;
import com.itxwl.shiroserver.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.OPTIONS;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
/**
 * 跨域解决
 */
@CrossOrigin
public class UserController {
    private UserService userService;

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    public Map<String, Object> saveUser(@RequestBody String userJson) {
        Map<String, Object> map = new HashMap<>();
        Object userObject = JSONObject.parseObject(userJson).get("userJson");
        User user = JSONObject.toJavaObject((JSON) userObject, User.class);
        if (StringUtils.isEmpty(user.toString())) {
            map.put("error", "传入数据不能为空!");
            return map;
        }
        Boolean isSuccess = false;
        try {
            isSuccess = userService.saveUser(user);
        } catch (Exception e) {
            map.put("error", e.getMessage());
        }
        if (isSuccess == true) {
            map.put("success", true);
            return map;
        }
        map.put("error", "保存失败!");
        return map;
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @PutMapping(value = "update")
    public Map<String, String> update(User user) {
        Map<String, String> map = new HashMap<>();
        try {
            userService.update(user);
        } catch (Exception e) {
            map.put("error", "修改失败");
            return map;
        }
        map.put("success", "修改成功");
        return map;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deletes/{id}", method = RequestMethod.DELETE,name = "deletes")
    public Map<String, Object> deletes(@PathVariable("id") String id) {
        Map<String, Object> map = new HashMap<>();
        if (id == null) {
            map.put("error", "请最少选择一条数据!");
            return map;
        }
        Integer integer = 0;
        try {
            integer = userService.deletes(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (integer >= 1 && integer <= 100) {
            map.put("success", true);
            return map;
        }
        map.put("error", "未知错误!");
        return map;
    }

    /**
     * 查询
     *
     * @param
     * @return
     */
    @GetMapping(value = "/selectAll/{id}")
    public ResponseEntity<User> selectById(@PathVariable("id") String id) {
        User user = null;
        try {
            user = userService.selectById(id);
        } catch (MyException e) {
            throw new MyException(e.getExceptionEnum());
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 查询所有以及搜索查询(用户)
     *
     * @param current
     * @param size
     * @param name
     * @return
     */
    @GetMapping(value = "findAll")
    public PageUtil<User> findAll(Integer current, Integer size, String name) {
        List<User> userList = userService.findAll(name);
        PageUtil<User> pageUtil = new PageUtil<>(current, size, userList);
        return pageUtil;
    }

    /**
     * 用户登录校验
     */
    @PostMapping(value = "Login")
    public Map<String, Object> Login(String name, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Boolean isSuccess = null;
        try {
            isSuccess = userService.Login(name, password);
        } catch (MyException e) {
            map.put("error", e.getExceptionEnum().getMessage());
            return map;
        }
        if (isSuccess) {
            map.put("success", true);
            return map;
        }
        map.put("error", "系统异常");
        return map;
    }

    /**
     * 根据不同的用户获得不同的权限
     * @param name
     * @return
     */
    @GetMapping(value = "findByNamePermission")
    public List<String> findByNamePermission(String name){
//        List<String> list=new LinkedList<>();
//        if (name.equals("4")){
//                list.add("updates");
//                list.add("deletes");
//        }else {
//            list.add("updates");
//        }
        return null;
    }

}
