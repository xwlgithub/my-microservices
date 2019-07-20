package com.itxwl.getoutserver.controller;

import com.itxwl.getoutserver.api.EnterFeign;
import com.itxwl.getoutserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/getenter")
public class GetEnterController {
    @Autowired
    private EnterFeign enterFeign;

    /**
     * 调用远程user接口
     * @return
     */
    @GetMapping(value = "/findAllUser")
    public List<User> findAllUser(){
        List<User> userList = null;
        try {
            userList = enterFeign.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
