package com.example.demo.MyTest;

import com.example.demo.DaoFactory.DaoFactory;
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
        DaoFactory daoFactory=new DaoFactory();
        daoFactory.getFriendDao().clearUMSG(1,2);
        daoFactory.getSqlSession().commit();

    }
}
