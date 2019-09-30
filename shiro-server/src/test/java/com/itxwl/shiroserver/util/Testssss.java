package com.itxwl.shiroserver.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Testssss {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void Tests(){
        System.out.println("得到的值"+jwtUtils);
    }
}
