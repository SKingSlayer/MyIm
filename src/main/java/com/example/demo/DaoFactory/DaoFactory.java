package com.example.demo.DaoFactory;

import com.example.demo.MyData.Dao.FriendDao;
import com.example.demo.MyData.Dao.TalkMessageDao;
import lombok.Data;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Data
public class DaoFactory {
    String resource ;
    InputStream inputStream ;
    SqlSessionFactory sqlSessionFactory ;
    SqlSession sqlSession ;
    FriendDao friendDao;
    TalkMessageDao talkMessageDao;


    public DaoFactory() throws IOException {
       resource = "mybatis-config.xml";
         inputStream = Resources.getResourceAsStream(resource);
         sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
         friendDao = sqlSession.getMapper(FriendDao.class);
         talkMessageDao=sqlSession.getMapper(TalkMessageDao.class);
    }
//
}