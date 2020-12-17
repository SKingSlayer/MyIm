package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.User;

import java.util.List;

public interface UserDao {
    List<User> getUserList();
    void insertUser(User user);
}
