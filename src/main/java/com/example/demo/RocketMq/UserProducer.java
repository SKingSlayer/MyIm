package com.example.demo.RocketMq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserProducer {

    @PostConstruct
    public void produder() {
        /**
         * 生产者的组名
         */

        String producerGroup = "user_group";
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setVipChannelEnabled(false);
        String namesrvAddr = "192.168.56.10:9876";
        producer.setNamesrvAddr(namesrvAddr);

        try {
            producer.start();
            for (int i = 0; i < 100; i++) {
                String userContent =String.valueOf(i)+"abc"+i;
                String jsonstr = JSON.toJSONString(userContent);
                System.out.println("发送消息:"+jsonstr);
                Message message = new Message("user-topic", "user-tag", jsonstr.getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult result = producer.send(message);
                System.err.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}