package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.FriendDao;
import com.example.demo.MyData.Dao.TalkMessageDao;
import com.example.demo.MyData.Entity.Friend;
import com.example.demo.MyData.Entity.TalkMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class TalkMessageTest {
    private static SqlSessionFactory sqlSessionFactory;
    public static  void main(String[] s) throws IOException {
        String resource = "mybatis-config.xml";
        ObjectMapper objectMapper=new ObjectMapper();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TalkMessageDao talkMessageDao = sqlSession.getMapper(TalkMessageDao.class);
        TalkMessage talkMessage=new TalkMessage();
        talkMessage.setFriendId(2);
        talkMessage.setUserId(1);
        talkMessage.setTalkTime(talkMessageDao.getTalkMessageById(105).getTalkTime());
        List<TalkMessage> lt=talkMessageDao.getTalkMessageByDate(talkMessage);
        System.out.println(objectMapper.writeValueAsString(lt));
//        List<Friend> friendList= friendDao.getFriendList(1);

//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        objectMapper.setDateFormat(fmt);
//        System.out.println(objectMapper.writeValueAsString(friendList));
//        friendDao.updateLastTalkTime(friend);
//        friendDao.addFriend(friend);

        sqlSession.commit();
        sqlSession.close();

    }
}