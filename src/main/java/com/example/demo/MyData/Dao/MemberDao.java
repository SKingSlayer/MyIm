package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.Member;
import com.example.demo.MyTest.DataSourceTest.DataSourceType;
import com.example.demo.MyTest.DataSourceTest.MyDataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Mapper
@Repository("MemberDao")
public interface MemberDao {
   void addMember(int userId,int groupId);
    Member getMember(int userId,int groupId);
    void addGroup(Member member);


    @Results(id = "memberMap", value = {
            @Result(id=true, column = "user_id", property = "userId"),
            @Result(column = "group_id", property = "groupId"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "group_name", property = "groupName"),
            @Result(column = "umsg", property = "umsg"),
            @Result(column = "time_stamp", property = "timeStamp")
    })

    @MyDataSource(value = DataSourceType.DB3)
    @Select("select * from member where user_id=1")
    List<Member> getAllGroup(int userId);
 @MyDataSource(value = DataSourceType.DB3)
 @Results(id = "Map", value = {
         @Result(id=true, column = "user_id", property = "userId"),
         @Result(column = "group_id", property = "groupId"),
         @Result(column = "user_name", property = "userName"),
         @Result(column = "group_name", property = "groupName"),
         @Result(column = "umsg", property = "umsg"),
         @Result(column = "time_stamp", property = "timeStamp")
 })
    @Select("select * from member where group_id=#{groupId}")
    List<Member> getAllMember(int groupId);

    @MyDataSource(value = DataSourceType.DB3)
    @Update("update member set time_stamp=#{timeStamp} where user_id=#{userId} and group_id=#{groupId}")
    void updateTimeStamp(int userId, int groupId, Date timeStamp);

    @MyDataSource(value = DataSourceType.DB3)
    @Update("update member set umsg=umsg+1 where user_id=#{userId} and group_id=#{groupId}")
    void updateUMSG(int userId, int groupId);

    @MyDataSource(value = DataSourceType.DB3)
    @Update("update member set umsg=0 where user_id=#{userId} and group_id=#{groupId}")
    void clearUMSG(int userId, int groupId);
}
