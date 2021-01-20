package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GroupDao {
   void addUser(int userId,int groupId);
   void addGroup(String groupName);
   int getLastId();
   Group getGroupById(int groupId);
   void updateMembership(int groupId);
}
