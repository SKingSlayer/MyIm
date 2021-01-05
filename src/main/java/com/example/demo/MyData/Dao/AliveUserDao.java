package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.AliveUser;

import java.util.Date;

public interface AliveUserDao {
    void upDateAliveUser(int userId, Date date);
    AliveUser getAliveUser(int userId);
    void addAliveUser(int userId, Date date);

}
