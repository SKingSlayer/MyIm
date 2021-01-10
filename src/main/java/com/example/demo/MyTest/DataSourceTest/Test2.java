package com.example.demo.MyTest.DataSourceTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.annotation.PostConstruct;

@Component
public class Test2 {
    @Autowired
    User2Dao user2Dao;
    @Autowired
    User1Dao user1Dao;
    @PostConstruct
    void hello(){
    System.out.println(user1Dao.queryUserById(1));
        System.out.println(user2Dao.queryUserById(1));
    }
}
