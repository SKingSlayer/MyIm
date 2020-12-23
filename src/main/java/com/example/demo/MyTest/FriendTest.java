package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.FriendDao;
import com.example.demo.MyData.Dao.TalkRecordListDao;
import com.example.demo.MyData.Entity.Friend;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class FriendTest {
    private static SqlSessionFactory sqlSessionFactory;
    public static  void main(String[] s) throws IOException {
        Date date=new Date();
        Friend friend=new Friend();
        friend.setFriendId(3);
        friend.setUserId(2);
        friend.setLastTalkTime(date);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FriendDao friendDao = sqlSession.getMapper(FriendDao.class);
//        List<Friend> friendList= friendDao.getFriendList(1);
//        ObjectMapper objectMapper=new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(friendList));
//        friendDao.updateLastTalkTime(friend);
        friendDao.addFriend(friend);
        sqlSession.commit();
        sqlSession.close();

    }
}
