package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.Member;
import com.example.demo.MyTest.DataSourceTest.DataSourceType;
import com.example.demo.MyTest.DataSourceTest.MyDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface MemberDao {
   void addMember(int userId,int groupId);
    List<Member> getAllMember(int groupId);
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
    @MyDataSource(value = DataSourceType.DB1)
    @Select("select * from member where user_id=1")
    List<Member> getAllGroup(int userId);
}
