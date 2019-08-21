package com.itxwl.shiroserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ShiroServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroServerApplication.class, args);
    }

}
