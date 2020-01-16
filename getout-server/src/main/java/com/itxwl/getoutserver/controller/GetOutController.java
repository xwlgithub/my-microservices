package com.itxwl.getoutserver.controller;

import com.itxwl.getoutserver.api.EnterFeign;
import com.itxwl.getoutserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getOut")
public class GetOutController {

    @Autowired
    private EnterFeign enterFeign;


    @GetMapping("/findEnterBySelectAll/{id}")
    public User findEnterBySelectAll(@PathVariable("id")String id){
        User users = enterFeign.selectAll(id);
        return users;
    }
}
