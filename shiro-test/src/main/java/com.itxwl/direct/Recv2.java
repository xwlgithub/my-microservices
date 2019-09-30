package com.itxwl.direct;

import com.itxwl.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

@SuppressWarnings("all")
public class Recv2 {
    public static final String EXCHANGE_NAME = "direct_exchange_test";

    public static final String QUEUE_NAME = "direct_exchange_queue_2";
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.geConnection();
        //从连接中创建通道
       final Channel channel = connection.createChannel();
        //声明(创建)队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //将队列绑定到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"select");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"insert");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"update");
        DefaultConsumer consumer=new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body);
                System.out.println("【消费者2】接收"+"\t"+msg+"");
                //手动确认消息消费ACK
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //监听队列,第二个参数,是否自动进行消息确认
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
