package com.example.demo.MyData.Dao;


import com.example.demo.MyData.Entity.Friend;
import com.example.demo.MyData.Config.DataSourceType;
import com.example.demo.MyData.Config.MyDataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@Mapper
public interface FriendDao {
     @Results(id ="friendMap",value={
             @Result(id = true,column = "user_id",property = "userId"),
             @Result(column = "friend_id",property = "friendId"),
             @Result(column = "friend_name",property = "friendName"),
             @Result(column = "umsg",property = "umsg"),
             @Result(column = "time_stamp",property = "timeStamp")
     })
     @Select("select * from friend_list where user_id=#{userId}")
     List<Friend> getFriendList(int userId);
     void addFriend(Friend friend);

     Date getLastTalkTime(int userId,int friendId);
     Date getTimeStamp(int userId,int friendId);



     @MyDataSource(value = DataSourceType.DB1)
     @Update("update friend_list set umsg=umsg+1 where user_id=#{userId} and friend_id=#{friendId}")
     void updateUMSG(int userId, int friendId);

     @MyDataSource(value = DataSourceType.DB1)
     @Update("update friend_list set time_stamp=#{timeStamp} where user_id=#{userId} and friend_id=#{friendId}")
     void updateTimeStamp(int userId, int friendId, Date timeStamp);

     @MyDataSource(value = DataSourceType.DB1)
     @Update("update friend_list set umsg=0 where user_id=#{userId} and friend_id=#{friendId}")
     void clearUMSG(int userId, int friendId);
}
