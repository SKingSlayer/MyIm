package com.example.demo.MyHandler;


import com.example.demo.MyData.Dao.RmDao;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.User;
import com.example.demo.MyData.JsonObject.FriendObject;
import com.example.demo.MyData.JsonObject.RmTmp;
import com.example.demo.RocketMq.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FriendsAquiringHandler extends TextWebSocketHandler {


    private static Logger logger = LoggerFactory.getLogger(FriendsAquiringHandler.class);
    @Autowired
    RedisTemplate<String, String> redisTemplate;


    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();




    public FriendsAquiringHandler() throws IOException {}

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {



    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        super.handleTextMessage(session, message);

        /**
         * 收到消息，自定义处理机制，实现业务
         */
        System.out.println("服务器收到消息：" + message.getPayload());
            String msg = message.getPayload();
            String test1 = "^test1";
            Pattern p1=Pattern.compile("^test1");
            Matcher m1=p1.matcher(msg);
            if(m1.find()) {
                msg = msg.replaceAll(test1, "");
                System.out.println(msg);
                System.out.println("new:" + msg);
                ObjectMapper objectMapper = new ObjectMapper();
                FriendObject friendObject = objectMapper.readValue(msg, FriendObject.class);
                UserDao useDao = sqlSession.getMapper(UserDao.class);
                RmDao rmDao = sqlSession.getMapper(RmDao.class);
                //        useDao.addMoney(2,50);
                //        rmDao.reduceMoney(1,1,50);
                User user = useDao.getUser(friendObject.getFriendName());
                if (user != null) {
                    System.out.println(user.getMoney());
                    String json = objectMapper.writeValueAsString(user);
                    TextMessage returnMessage = new TextMessage(json);
                    System.out.println(returnMessage);
                    session.sendMessage(returnMessage);
                } else {
                    System.out.println("nullpointexception!!");
                    TextMessage returnMessage = new TextMessage("没有数据");
                    System.out.println(returnMessage);
                    session.sendMessage(returnMessage);
                }
            }



    }


    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }


}