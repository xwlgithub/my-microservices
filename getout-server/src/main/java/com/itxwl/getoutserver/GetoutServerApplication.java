package com.itxwl.getoutserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
/**
 * 开启远程调用注解
 */
@EnableFeignClients
public class GetoutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetoutServerApplication.class, args);
    }

}
