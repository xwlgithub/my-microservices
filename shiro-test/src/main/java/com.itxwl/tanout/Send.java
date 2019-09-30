package com.itxwl.tanout;

import com.itxwl.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * fanout消息的发送与接收
 */

/**
 * 生产者
 */
@SuppressWarnings("all")
public class Send {
    public static final String EXCHANGE_NAME = "fanout_exchange_test";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection=null;
        Channel channel=null;
        try {
             connection = ConnectionUtil.geConnection();
            //从连接中创建通道
             channel = connection.createChannel();
            //声明exchange,指定类型为fanout
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            //消息内容
            String message="Hello exchange";
            //向指定的队列中发送消息
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("向交换机发送消息成功");
        } finally {
            channel.close();
            connection.close();
        }
    }
}
