package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.AliveUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface AliveUserDao {
    @Update("update alive_user set time_stamp=#{timeStamp} where user_id=#{userId}")
    void upDateAliveUser(int userId, Date timeStamp);
    @Results(id="aliveUserMap" ,value = {
            @Result(id=true, column = "user_id", property = "userId"),
            @Result(column = "time_stamp", property = "timeStamp"),
    })
    @Select("select * from alive_user")
    List<AliveUser> getAliveUser();
    @Insert("insert into alive_user values(#{userId},#{timeStamp})")
    void addAliveUser(int userId, Date timeStamp);
    @Delete("delete from alive_user where user_id=#{userId}")
    void deleteAliveUserBy(int userId) ;
    @ResultMap(value= {"aliveUserMap"})
    @Select("select * from alive_user where user_id=#{userId}")
    AliveUser getAliveUserById(int userId);
}
