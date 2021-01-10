package com.example.demo.MyTest.DataSourceTest;



import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class MyTest {
    @Autowired
   static   private User1Dao user1Dao;
    @Autowired
    static  private User2Dao user2Dao;

    public static  void main(String[] s) throws IOException {
        System.out.println(user1Dao.queryUserById(1));
        System.out.println(user1Dao.queryUserById(2));

    }
}
