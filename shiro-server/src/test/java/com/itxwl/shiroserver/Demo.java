package com.itxwl.shiroserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class Demo {
    /**
     * 校验邮箱格式
     */
    @Test
    public void TestEmail() {
        String emailIsHave = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+)+$";
        String email = "2509647976@QQ.com";

        if (email.matches(emailIsHave)) {
            System.out.println("合法邮箱地址");
        } else {
            System.out.println("不合法邮箱地址");
        }
    }
}
