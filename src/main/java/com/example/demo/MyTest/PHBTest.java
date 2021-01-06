package com.example.demo.MyTest;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Entity.AliveUser;
import com.example.demo.MyData.Entity.PHB;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PHBTest {

        public static  void main(String[] s) throws IOException {
            DaoFactory daoFactory=new DaoFactory();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          System.out.println(daoFactory.getPhbDao().getLastId());

        }




}
