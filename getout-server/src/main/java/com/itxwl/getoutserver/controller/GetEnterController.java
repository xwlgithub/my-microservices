package com.itxwl.getoutserver.controller;

import com.itxwl.getoutserver.api.EnterFeign;
import com.itxwl.getoutserver.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/getenter")
@Api(value = "测试起来",tags = "测试")
public class GetEnterController {
    @Autowired
    private EnterFeign enterFeign;

    /**
     * 调用远程user接口
     * @return
     */
    @ApiOperation(value = "分页",notes ="有一个参数",httpMethod = "GET")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "name", value = "姓名参数",paramType = "query",
                    dataType = "String",required = true)})
    @GetMapping(value = "/findAllUser")
    public List<User> findAllUser(@RequestParam("name") String name){
        System.out.println("参数"+name);
        List<User> users=new LinkedList<>();
        for (int i = 0; i <5; i++) {
              User user=new User();
              user.setName(String.valueOf((int) (Math.random() * 10+1)));
              user.setEmail("邮箱"+i+1);
              user.setName("姓名"+i+1);
              users.add(user);
        }
//        List<User> userList = null;
//        try {
//            userList = enterFeign.selectAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return users;
    }
    @ApiOperation(value = "修改",notes ="对象",httpMethod = "PUT")
    @GetMapping(value = "/update")
    public List<User> save(@RequestParam("name") String name){
        System.out.println("参数"+name);
        List<User> users=new LinkedList<>();
        for (int i = 0; i <5; i++) {
            User user=new User();
            user.setName(String.valueOf((int) (Math.random() * 10+1)));
            user.setEmail("邮箱"+i+1);
            user.setName("姓名"+i+1);
            users.add(user);
        }
//        List<User> userList = null;
//        try {
//            userList = enterFeign.selectAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return users;
    }
}
