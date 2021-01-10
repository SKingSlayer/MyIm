package com.example.demo.MyTest;

import com.example.demo.DaoFactory.DaoFactory;

import java.io.IOException;
import java.util.Date;

public class testAliveUser {
   public static void main(String[] s) throws IOException {
        DaoFactory daoFactory=new DaoFactory();
       daoFactory.getSqlSession().clearCache();
        daoFactory.getAliveUserDao().getAliveUser(2).getLastTalkTime();

    }
}
