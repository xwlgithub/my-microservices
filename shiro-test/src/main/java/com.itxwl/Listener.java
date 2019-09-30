package com.itxwl;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    /**
     * 声明队列
     * @param message
     */
    //@RabbitListener(queues = "simple_queue")

    /**
     * 监听消息->消费消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(value =
    @Queue(value = "simple.test.queue",durable = "true"),
    exchange =@Exchange(value = "spring.test.exchange",type = ExchangeTypes.TOPIC),
    key = "#.#"))
    public void Listen(String message){
        System.out.println("接收的消息"+message);
    }
}
