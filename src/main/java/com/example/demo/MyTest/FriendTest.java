package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.FriendDao;
import com.example.demo.MyData.Entity.Friend;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FriendTest {
    private static SqlSessionFactory sqlSessionFactory;
    public static  void main(String[] s) throws IOException {
        Date date=new Date();
        Friend friend=new Friend();
        friend.setFriendId(3);
        friend.setUserId(2);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FriendDao friendDao = sqlSession.getMapper(FriendDao.class);
        Date date1 =friendDao.getLastTalkTime(1,3);
//        List<Friend> friendList= friendDao.getFriendList(1);
//        ObjectMapper objectMapper=new ObjectMapper();
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        objectMapper.setDateFormat(fmt);
//        System.out.println(objectMapper.writeValueAsString(friendList));
//        friendDao.addFriend(friend);
        System.out.println(date1);
        sqlSession.commit();
        sqlSession.close();

    }
}
