package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static  void main(String[] s) throws IOException {
      String k="test1{\"username\":\"11111\",\"friendName\":\"2\"}";
        Pattern p=Pattern.compile("^test1");
        Matcher m=p.matcher(k);
        System.out.println(m.find());



    }

}
