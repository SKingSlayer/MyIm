package com.example.demo.MyData.Dao;


import com.example.demo.MyData.Entity.TalkMessage;


import java.sql.Date;
import java.util.List;

public interface TalkMessageDao {
     //List<CharHistory> getHistoryByDay(Date date);
     //CharHistory getHistoryByNum(int num);
     void saveTalkMessage(TalkMessage tm);
     List<TalkMessage> getTalkMessageByDate(TalkMessage tm);
     TalkMessage getTalkMessageById(int i);

}
