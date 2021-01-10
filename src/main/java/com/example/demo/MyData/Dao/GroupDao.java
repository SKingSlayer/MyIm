package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.Group;

public interface GroupDao {
   void getAllGroup(int userId);
   void addUser(int userId,int groupId);
   void addGroup(String groupName);
   int getLastId();
   Group getGroupById(int groupId);
   void updateMembership(int groupId);
}
