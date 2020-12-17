package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.HistoryDao;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.CharHistory;
import com.example.demo.MyData.Entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MyTest1 {
    private static SqlSessionFactory sqlSessionFactory;
    public static  void main(String[] s) throws IOException {
        Date date=new Date();
        CharHistory charHistory=new CharHistory();
        charHistory.setMyTime(date);
        charHistory.setRecv(1);
        charHistory.setSend(1);
        charHistory.setHistory("11111");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        HistoryDao historyDao = sqlSession.getMapper(HistoryDao.class);
        historyDao.setHistory(charHistory);
        sqlSession.commit();
        sqlSession.close();

    }
}
