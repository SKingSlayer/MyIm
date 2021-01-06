package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.User;

import java.util.List;

public interface UserDao {
    List<User> getUserList();
    void registerUser(User user);
    void addMoney(int userId,int money);
     User getUser(int userId);
     void reduceMoney(int userId,int money);
     int  getMoney(int userId);
}
