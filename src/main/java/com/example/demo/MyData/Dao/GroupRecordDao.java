package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.GroupRecord;

import java.util.Date;

public interface GroupRecordDao {
void getGroupRecord(int groupId, Date timeStamp);
   void addGroupRecord(GroupRecord groupRecord);
}
