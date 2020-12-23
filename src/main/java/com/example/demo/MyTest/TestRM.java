package com.example.demo.MyTest;

import com.example.demo.MyData.Dao.RmDao;

import com.example.demo.MyData.Entity.Rm;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class TestRM {

        private static SqlSessionFactory sqlSessionFactory;
        public static  void main(String[] s) throws IOException {

            Rm rm=new Rm();
            rm.setId(1);
            rm.setRmNum(500);
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            RmDao rmDao= sqlSession.getMapper(RmDao.class);
            //rmDao.saveRm(rm);
            System.out.println(rmDao.getRmNum(1));
            sqlSession.commit();
            sqlSession.close();


    }
}
