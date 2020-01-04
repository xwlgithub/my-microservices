package com.itxwl.elasticsearchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ElasticSearchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchServerApplication.class, args);
    }

}
