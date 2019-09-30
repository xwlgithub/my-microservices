package com.itxwl.direct;

import com.itxwl.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

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
    public static final String EXCHANGE_NAME = "direct_exchange_test";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection=null;
        Channel channel=null;
        try {
             connection = ConnectionUtil.geConnection();
            //从连接中创建通道
             channel = connection.createChannel();
             //生产者确认
            //channel.confirmSelect();
            //事务
            //channel.txSelect();
            //声明exchange,指定类型为fanout
            /**
             * 后面加一个true  持久化
             */
            channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);

            //消息内容
            String message="商品修改 id = 1001";
            //向指定的队列中发送消息
            /**
             * MessageProperties.PERSISTENT_TEXT_PLAIN
             */
            channel.basicPublish(EXCHANGE_NAME,"select", MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("商品需要修改!"+message);
            //超时时长
            // channel.waitForConfirms(5000);
        } finally {
            channel.close();
            connection.close();
        }
    }
}
