package com.itxwl.shiroserver.controller;

import com.itxwl.shiroserver.entity.UserInformation;
import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.ExceptionResult;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.service.LoginJwtProceService;
import com.itxwl.shiroserver.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginJwtProceService loginJwtProceService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * shiro认证登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/loginFrom")
    public String login(String username, String password, Model model) {
        String session=null;
        try {
            //shiro Token处理类
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //获得提交认证类
            Subject subject = SecurityUtils.getSubject();
            //获取session
             session = (String)subject.getSession().getId();
            subject.login(token);
        } catch (UnknownAccountException e) {
            model.addAttribute("message", e.getMessage());
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("message", "用户名或密码错误!");
            return "login";
        } catch (AuthenticationException e) {
            model.addAttribute("message", e.getMessage());
            return "login";
        }
        model.addAttribute("sessionId", session);
        return "success";
    }

    /**
     * 用户JWTtocken认证返回
     *
     * @return
     */
    @PostMapping(value = "/LoginJwtProce")
    public ResponseEntity<ExceptionResult> LoginJwtProce(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String toeken = null;
        try {
            toeken = loginJwtProceService.findJwtProce(username, password);
        } catch (MyException e) {
            throw new MyException(e.getExceptionEnum());
        }
        return ResponseEntity.ok(new ExceptionResult(ExceptionEnum.SUCCESS, toeken));
    }

    /**
     * 携带tocken获取用户信息
     *
     * @return
     */
    @PostMapping(value = "getJwtWithHave")
    public ResponseEntity<UserInformation> getJwtWithHave(HttpServletRequest request) {
        String authentication = request.getHeader("Authentication");
        if (StringUtils.isEmpty(authentication)) {
            throw new MyException(ExceptionEnum.NOT_LOGIN);
        }
        String tocken = authentication.replace("Baere ", "");
        Claims claims = jwtUtils.parseJwt(tocken);
        String username = claims.getId();
        //获得用户所属角色以及权限
        UserInformation userInformation = loginJwtProceService.findUserInformation(username);
        return ResponseEntity.ok(userInformation);
    }

    /**
     * 获取session标识信息
     * @param session
     * @return
     */
    @GetMapping(value = "/findSessionIds")
    public ResponseEntity<String> findSessionIds(HttpSession session) {
        Enumeration<?> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            Object valye = session.getAttribute(name);
            System.out.println("<B>"+name+"</B>"+valye+"<br>/n");
        }
        return ResponseEntity.ok("success");
    }



    /**
     * 携带tocken获取用户信息
     *
     * @return
     */
    @PostMapping(value = "/findGetJwtLogin")
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
     *
     * @return
     */
    @RequestMapping(value = "/find")
    public String goToFind() {
        return "find";
    }

    /**
     * 删除数据页面
     *
     * @return
     */
    @RequiresPermissions(value = "user-delete")
    @RequestMapping(value = "/delete")
    public String goToDelete() {
        return "delete";
    }

    /**
     * 跳转成功页面
     *
     * @return
     */
    @RequestMapping(value = "/success")
    public String goTosuccess() {
        return "success";
    }

    /**
     * 未授权(也就是没有权限的页面)
     *
     * @return
     */
    @RequestMapping(value = "/noauth")
    public String goTonoauth() {
        return "noauth";
    }
}
