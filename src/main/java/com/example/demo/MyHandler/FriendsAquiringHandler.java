package com.example.demo.MyHandler;


import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Dao.FriendDao;
import com.example.demo.MyData.Dao.RmDao;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.Friend;
import com.example.demo.MyData.Entity.TalkMessage;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FriendsAquiringHandler extends TextWebSocketHandler {


    private static Logger logger = LoggerFactory.getLogger(FriendsAquiringHandler.class);
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    static Map<String,WebSocketSession> sessionMap=new HashMap<>();
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();




    public FriendsAquiringHandler() throws IOException {}

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println(session.getId());


    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        super.handleTextMessage(session, message);
        ObjectMapper objectMapper = new ObjectMapper();
        DaoFactory daoFactory=new DaoFactory();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(fmt);
        /**
         * 收到消息，自定义处理机制，实现业务
         */
        System.out.println("服务器收到消息：" + message.getPayload());

        String msg = message.getPayload();
        Pattern ph=Pattern.compile("^hello");
        Matcher mh=ph.matcher(msg);
        if(mh.find()){

            msg=msg.replaceAll("^hello","");
            TalkMessage talkMessage= objectMapper.readValue(msg,TalkMessage.class);
            String currentSession=talkMessage.getUserId()+""+talkMessage.getFriendId();
            String targetSession=talkMessage.getFriendId()+""+talkMessage.getUserId();
            sessionMap.put(currentSession, session);
            Date date= daoFactory.getFriendDao().getLastTalkTime(talkMessage.getUserId(),talkMessage.getFriendId());
            talkMessage.setTalkTime(date);
            List<TalkMessage> lt= daoFactory.getTalkMessageDao().getTalkMessageByDate(talkMessage);
            if(lt!=null)
            {
                String s= objectMapper.writeValueAsString(lt);
                System.out.println(s);
                TextMessage textMessage=new TextMessage("ack"+s);//不要忘记commit
                session.sendMessage(textMessage);
            }


            return;
        }
        Pattern pk=Pattern.compile("^ack");
        Matcher mk=pk.matcher(msg);
        if(mk.find()){
            msg=msg.replaceAll("^ack","");
            Friend friend=objectMapper.readValue(msg,Friend.class);
            daoFactory.getFriendDao().updateLastTalkTime(friend);
            daoFactory.getSqlSession().commit();
            System.out.println("ack:"+friend.getUserId()+" " +friend.getFriendId());
            TextMessage textMessage=new TextMessage("以确认");
            session.sendMessage(textMessage);
        }


            String test1 = "^test1";
            Pattern p1=Pattern.compile("^test1");
            Matcher m1=p1.matcher(msg);
            if(m1.find()) {
                msg = msg.replaceAll(test1, "");
                System.out.println(msg);
                System.out.println("new:" + msg);

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
                    return;
                }
            }
            Pattern pf=Pattern.compile("^getFriends");
            Matcher mf=pf.matcher(msg);
            if(mf.find()){
                msg=msg.replaceAll("^getFriends","");
//                List<Friend> friendList=daoFactory.getFriendDao().getFriendList(Integer.parseInt(msg));
//                String fl=objectMapper.writeValueAsString(friendList);
//                System.out.println(fl);
//                TextMessage returnMessage = new TextMessage(fl);
//                session.sendMessage(returnMessage);
                return;
            }
        Pattern pa=Pattern.compile("^addFriend");
        Matcher ma=pa.matcher(msg);
        if(ma.find()){
            msg=msg.replaceAll("^addFriend","");
            Friend friend= objectMapper.readValue(msg,Friend.class);
            daoFactory.getFriendDao().addFriend(friend);
            TextMessage returnMessage = new TextMessage("成功添加好友");
            session.sendMessage(returnMessage);
            daoFactory.getSqlSession().commit();
        }
        Pattern pm=Pattern.compile("^sendMessage");
        Matcher mm=pm.matcher(msg);
        if(mm.find()){
            msg=msg.replaceAll("^sendMessage","");
            TalkMessage talkMessage= objectMapper.readValue(msg,TalkMessage.class);
            String currentSession=talkMessage.getUserId()+""+talkMessage.getFriendId();
            String targetSession=talkMessage.getFriendId()+""+talkMessage.getUserId();
            System.out.println("send"+talkMessage.getUserId()+talkMessage.getFriendId());
            if(sessionMap.get(targetSession)!=null)//error: talkMessage.getFriendId()!=0
            {
                TextMessage textMessage=new TextMessage(talkMessage.message);
                sessionMap.get(targetSession).sendMessage(textMessage);
            }
            talkMessage.setTalkTime(new Date());
            daoFactory.getTalkMessageDao().saveTalkMessage(talkMessage);

            TextMessage returnMessage = new TextMessage("成功发送消息");
            session.sendMessage(returnMessage);
            daoFactory.getSqlSession().commit();

        }




    }


    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }


}