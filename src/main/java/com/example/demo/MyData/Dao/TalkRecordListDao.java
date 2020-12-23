package com.example.demo.MyData.Dao;


import com.example.demo.MyData.Entity.TalkRecord;

import java.sql.Date;
import java.util.List;

public interface TalkRecordListDao {
     //List<CharHistory> getHistoryByDay(Date date);
     //CharHistory getHistoryByNum(int num);
     void saveTalkRecord(TalkRecord c);

}
