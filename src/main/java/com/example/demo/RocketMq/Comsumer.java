package com.example.demo.RocketMq;

import com.example.demo.MyData.Dao.RmDao;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.JsonObject.RmTmp;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

//@Component
public class Comsumer {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    RmDao rmDao= sqlSession.getMapper(RmDao.class);
    UserDao userDao=sqlSession.getMapper(UserDao.class);
    ObjectMapper objectMapper = new ObjectMapper();

    public Comsumer() throws IOException {
    }

//    @PostConstruct
    public void handleRM() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-group");

        consumer.setNamesrvAddr("192.168.56.10:9876");
        consumer.setInstanceName("rmq-instance");
        consumer.subscribe("rm-topic", "user-tag");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                int rmNum;
                for (MessageExt msg : msgs) {
                    System.out.println("222");
                    try {
                        RmTmp rmTmp = objectMapper.readValue(msg.getBody(), RmTmp.class);
                        System.out.println("消费者消费数据:"+new String(msg.getBody()));
                        int rmId=Integer.parseInt(rmTmp.getRmId());
                        int userId=Integer.parseInt(rmTmp.getUserId());
                        //rmDao.saveRm(rm);
                        System.out.println(" rmId "+rmId);
                        rmNum= rmDao.getRmNum(rmId);


                        if(rmNum>=10){
                            rmDao.reduceMoney(rmId,userId,10);
                            userDao.addMoney(userId,10);
                        }

                        System.out.println("333");
                        sqlSession.commit();

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
