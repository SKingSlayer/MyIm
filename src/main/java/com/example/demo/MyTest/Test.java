package com.example.demo.MyTest;


import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.User;
import org.apache.catalina.core.ApplicationContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

public class Test {

    private static SqlSessionFactory sqlSessionFactory;
    public static  void main(String[] s) throws IOException {
        User user1=new User();
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao useDao = sqlSession.getMapper(UserDao.class);
        List<User> userList = useDao.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.commit();
        sqlSession.close();
    }

}
