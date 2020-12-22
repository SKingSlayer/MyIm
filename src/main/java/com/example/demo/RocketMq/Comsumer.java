package com.example.demo.RocketMq;

import com.example.demo.MyData.JsonObject.RmTmp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;

import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;



import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
@Component
public class Comsumer {
    ObjectMapper objectMapper = new ObjectMapper();
    @PostConstruct
    public void handleRM() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-group");

        consumer.setNamesrvAddr("192.168.56.10:9876");
        consumer.setInstanceName("rmq-instance");
        consumer.subscribe("rm-topic", "user-tag");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("消费者消费数据:"+new String(msg.getBody()));
                    try {
                        RmTmp rmTmp = objectMapper.readValue(msg.getBody(), RmTmp.class);
                        System.out.println("RmId: "+rmTmp.getRmId());
                        System.out.println("SessionId: "+rmTmp.getSessionId());
                        System.out.println("UserId: "+rmTmp.getUserId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}