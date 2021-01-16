package com.example.demo.RocketMq;

import com.example.demo.MyData.Dao.GHBDao;
import com.example.demo.MyData.Dao.RmDao;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.GHB;
import com.example.demo.MyData.JsonObject.RmTmp;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;

import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;



import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class MyConsumer1 {
    @Autowired
    GHBDao ghbDao;
    @Autowired
    UserDao userDao;
        @PostConstruct
        @Transactional
    public void handleRM() throws MQClientException {
            ObjectMapper objectMapper=new ObjectMapper();
            SimpleDateFormat slf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            objectMapper.setDateFormat(slf);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr("192.168.56.10:9876");
        consumer.setConsumerGroup("test-group2");
        consumer.setInstanceName("rmq-instance1");
        consumer.subscribe("rm-topic", "user-tag");
        consumer.setConsumeMessageBatchMaxSize(10);
        //设置广播消费
//        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @SneakyThrows
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                int rmNum;
                for (MessageExt msg : msgs) {
                    GHB ghb= objectMapper.readValue(new String(msg.getBody()), GHB.class);

                    double num=ghb.getMoney();

                    synchronized(this){
                        double currentMoney=ghbDao.getMoney(ghb.getId());
                        if(currentMoney>=num)
                        {
                            ghbDao.subMoney1(ghb.getId(),currentMoney,num);
                            userDao.addMoney(ghb.getUserId(),num);
                            log.info("hello");
                            log.info(String.valueOf("user  "+userDao.getMoney(1)));
                            log.info(String.valueOf("ghb   "+ ghbDao.getMoney(ghb.getId())));

                        }
                    }


                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
