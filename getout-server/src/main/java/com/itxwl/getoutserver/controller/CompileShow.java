package com.itxwl.getoutserver.controller;

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

    @GetMapping(value = "/findAtimerByName")
    public String findAtimerByName(String id){
      return   compileShowService.findAtimerByName(id);
    }

}
