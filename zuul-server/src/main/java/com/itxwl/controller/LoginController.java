package com.itxwl.controller;


import com.itxwl.shiroserver.entiry.User;
import com.itxwl.util.JwtUtils;
import com.itxwl.util.ParamEveryUtil;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@SuppressWarnings("all")
@RequestMapping("/auth")
@CrossOrigin
@AllArgsConstructor
public class LoginController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * shiro认证登录
     *返回tocken信息
     * @param username
     * @param password
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "loginFrom")
    public Map<String, Object> login(@RequestParam String name, @RequestParam String password) {
        Map<String, Object> map = new HashMap<>();
        Boolean isSuccess = null;
        //shiro Token处理类
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //获得提交认证类
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            map.put("error", ParamEveryUtil.NAME_PSD_IS_ERROR);
            return map;
        }
        map.put("success", true);
        User user = (User) request.getAttribute(name);
        Map<String, Object> authMap = new HashMap<>();
        authMap.put(user.getName(), user.getPassword());
        map.put("tocken", jwtUtils.createJwt(user.getId(), new Date().toString(), authMap));
        return map;
    }
}
