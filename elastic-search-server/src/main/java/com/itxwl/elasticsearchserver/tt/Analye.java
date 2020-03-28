package com.itxwl.elasticsearchserver.tt;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
@Component
public class Analye {
    @Bean
    public ExecutorService executorService(){
        return new ThreadPoolExecutor(2,
                10,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque(5), //队列慎配置，核心线程数满的情况，队列越大排队越多，不会开启新线程
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }
}
