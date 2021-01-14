package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.Group;
import com.example.demo.MyTest.DataSourceTest.DataSourceType;
import com.example.demo.MyTest.DataSourceTest.MyDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GroupDao {
   void addUser(int userId,int groupId);
   void addGroup(String groupName);
   int getLastId();
   Group getGroupById(int groupId);
   void updateMembership(int groupId);
}
