package com.itxwl.shiroserver.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    /**
     * shiro认证登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/loginFrom")
    public String login(String username, String password,Model model) {
        try {
            //shiro Token处理类
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //获得提交认证类
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (UnknownAccountException e){
            model.addAttribute("message",e.getMessage());
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("message","用户名或密码错误!");
            return "login";
        } catch (AuthenticationException e) {
            model.addAttribute("message",e.getMessage());
            return "login";
        }
        return "success";
    }

    /**
     * 跳转登录页
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String goToLogin() {
        return "login";
    }

    /**
     * 查看数据页面
     * @return
     */
    @RequestMapping(value = "/find")
    public String goToFind() {
        return "find";
    }
    /**
     * 查看数据页面
     * @return
     */
    @RequestMapping(value = "/delete")
    public String goToDelete() {
        return "delete";
    }
    /**
     * 跳转成功页面
     * @return
     */
    @RequestMapping(value = "/success")
    public String goTosuccess() {
        return "success";
    }

    /**
     * 未授权(也就是没有权限的页面)
     * @return
     */
    @RequestMapping(value = "/noauth")
    public String goTonoauth() {
        return "noauth";
    }
}
