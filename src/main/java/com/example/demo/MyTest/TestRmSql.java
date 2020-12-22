package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.RmDao;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestRmSql {
    private static SqlSessionFactory sqlSessionFactory;
    public static  void main(String[] s) throws IOException {
        com.example.demo.MyData.Entity.User user1=new com.example.demo.MyData.Entity.User();
        user1.setId(1);
        user1.setMoney(300);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao useDao = sqlSession.getMapper(UserDao.class);
        RmDao rmDao=sqlSession.getMapper(RmDao.class);
//        useDao.addMoney(2,50);
//        rmDao.reduceMoney(1,1,50);
        User user= useDao.getUser(3);
        System.out.println(user.getMoney());
        sqlSession.commit();
        sqlSession.close();

    }
}
