package com.example.demo.utils;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyTest.DataSourceTest.User1Dao;
import com.example.demo.MyTest.DataSourceTest.User2Dao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;

@Transactional
public class MyThread implements Runnable {
    DaoFactory daoFactory;

   @SneakyThrows
   public void run(){


    }
}
