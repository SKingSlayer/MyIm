package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.ChatRecord;

import java.util.Date;
import java.util.List;

public interface ChatRecordDao {
    List<ChatRecord> getRecord(int userId,int friendId,Date date);
    void addRecord(ChatRecord chatRecord);
}
