package com.itxwl.securityserver.controller;

import com.itxwl.entity.Demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getData")
public class DataController {

    @GetMapping(value = "/findName")
    public String getName(){
        return  "张三";
    }
}
