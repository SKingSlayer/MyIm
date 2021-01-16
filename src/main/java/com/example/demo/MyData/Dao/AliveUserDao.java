package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.AliveUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface AliveUserDao {
    void upDateAliveUser(int userId, Date date);
    AliveUser getAliveUser(int userId);
    void addAliveUser(int userId, Date date);

    void deleteAliveUser(int userId) ;

}
