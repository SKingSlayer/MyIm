package com.example.demo.MyTest;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.AliveUser;
import com.example.demo.MyData.Entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class TestAlive {
    public static  void main(String[] s) throws IOException {
        DaoFactory daoFactory=new DaoFactory();
        int userId=1;
        AliveUser aliveUser= daoFactory.getAliveUserDao().getAliveUser(userId);
        System.out.println(aliveUser.getUserId());
        daoFactory.getAliveUserDao().upDateAliveUser(1,new Date());
        daoFactory.getSqlSession().commit();
        }


}
