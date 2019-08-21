package com.itxwl.shiroserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
/**
 * 交由注册中心管理
 */
@EnableEurekaClient
public class EnterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterServerApplication.class, args);
    }

}
