package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserDao {
    List<User> getUserList();

    @Insert("insert into user (username,password,email,money) values(#{userName},#{password},#{email},#{money})")
    void registerUser(User user);
   @Update("update user set money=money+#{money} where user_id=#{userId} ")
    void addMoney(int userId,double money);
   @Select("select * from user where user_id=#{userId}")
     User getUser(int userId);
     void reduceMoney(int userId,double money);
     @Select("select money from user where user_id=#{userId}")
     int  getMoney(int userId);

     @Update("update user set money=money-#{money} where user_id=#{userId} ")
     void subMoney(int userId,double money);
    @Results(id="MyUserMap" ,value = {
            @Result(id=true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "email", property = "email"),
            @Result(column = "money", property = "money"),
    })
    @Select("select * from user where username=#{username}")
    User getUserByName(String username);
    @Update("update user set money=#{money} where user_id=#{userId} ")
     void setMoney(int userId,double money);
    Object findById(int id);
    Object findByName(String name);
}
