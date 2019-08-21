package com.itxwl.shiroserver.controller;

import com.itxwl.shiroserver.exception.MyException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
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
    //@ResponseBody
    public String login(String username, String password) {
       // Map<String, Object> map = new HashMap<>();
        //shiro Token处理类
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获得提交认证类
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            subject.checkRoles("admin");
            subject.checkPermissions("user:delete","user:select");
        }catch (MyException e){
            throw  new MyException(e.getExceptionEnum());
        }
//        } catch (IncorrectCredentialsException e) {
//           map.put("error","用户名或密码错误!");
//        }catch (UnknownAccountException e){
//            map.put("error","账号不存在!");
//        }catch (AuthenticationException e){
//            map.put("error","用户名或密码错误!");
//        }
        //map.put("success", true);
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
}
