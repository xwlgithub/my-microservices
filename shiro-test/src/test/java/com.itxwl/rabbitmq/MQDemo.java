package com.itxwl.rabbitmq;

import com.itxwl.ShiroTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShiroTest.class)
public class MQDemo {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Test
    public void testSend() throws Exception{
        String message="你好 Spring";
        this.amqpTemplate.convertAndSend("spring.test.exchange","a.c",message);
        Thread.sleep(10000);
    }
}
