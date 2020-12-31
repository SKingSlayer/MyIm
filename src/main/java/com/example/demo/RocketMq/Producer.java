package com.example.demo.RocketMq;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

//@Component
public class Producer {
    DefaultMQProducer producer = new DefaultMQProducer("test-group");
    Producer() throws MQClientException {
        producer.setNamesrvAddr("192.168.56.10:9876");
        producer.setInstanceName("rmq-instance");
        producer.start();
    }
    public void sendMessage(String s) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message("rm-topic", "user-tag", s.getBytes());
        producer.send(message);
        System.out.println("生产者发送消息:" + s);
    }
}
