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

            String x="{\"senderId\":\"1\",\"receiverId\":2,\"money\":60,\"timeStamp\":\"2021-01-05 16:11:06\"}";
            int userId=1;
            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.setDateFormat(fmt);
            PHB phb=objectMapper.readValue(x,PHB.class);
          daoFactory.getPhbDao().addPHB(phb);
          System.out.println(phb.getMoney());
            daoFactory.getSqlSession().commit();
        }




}
