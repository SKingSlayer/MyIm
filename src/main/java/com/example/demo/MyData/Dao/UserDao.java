package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserDao {
    List<User> getUserList();
    void registerUser(User user);
   @Update("update user set money=money+#{money} where user_id=#{userId} ")
    void addMoney(int userId,double money);
     User getUser(int userId);
     void reduceMoney(int userId,double money);
     @Select("select money from user where user_id=#{userId}")
     int  getMoney(int userId);

     @Update("update user set money=money-#{money} where user_id=#{userId} ")
     void subMoney(int userId,double money);

    @Update("update user set money=#{money} where user_id=#{userId} ")
     void setMoney(int userId,double money);
    Object findById(int id);
    Object findByName(String name);
}
