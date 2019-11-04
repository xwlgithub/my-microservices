package com.itxwl.getoutserver.controller;

import com.itxwl.getoutserver.config.ConfigurationMaster;
import com.itxwl.getoutserver.service.CompileShowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
@AllArgsConstructor
@CrossOrigin
public class CompileShow {
    private CompileShowService compileShowService;
    private ConfigurationMaster configurationMaster;
    @GetMapping(value = "/findAtimerByName")
    public String findAtimerByName(){
        System.out.println(configurationMaster.getUrl());
        System.out.println(configurationMaster.getDriverClassName());
        System.out.println(configurationMaster.getPassword());
        System.out.println(configurationMaster.getUsername());
      return   compileShowService.findAtimerByName();
    }

}
