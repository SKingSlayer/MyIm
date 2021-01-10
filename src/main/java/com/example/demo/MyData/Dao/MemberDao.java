package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.Member;

import java.util.List;

public interface MemberDao {
   void addMember(int userId,int groupId);
    List<Member> getAllMember(int groupId);
    Member getMember(int userId,int groupId);
    void addGroup(Member member);
}
