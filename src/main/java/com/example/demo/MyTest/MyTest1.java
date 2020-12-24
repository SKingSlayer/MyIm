package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.TalkMessageDao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MyTest1 {
    private static SqlSessionFactory sqlSessionFactory;
    public static  void main(String[] s) throws IOException {
        Date date=new Date();

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TalkMessageDao historyDao = sqlSession.getMapper(TalkMessageDao.class);
        sqlSession.commit();
        sqlSession.close();

    }
}
