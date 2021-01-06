package com.example.demo.MyTest;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Entity.AliveUser;
import com.example.demo.MyData.Entity.ChatRecord;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TestRecord {
    public static  void main(String[] s) throws IOException {
        DaoFactory daoFactory=new DaoFactory();
        ChatRecord chatRecord=new ChatRecord();
        chatRecord.setFriendId(1);
        chatRecord.setUserId(2);
        chatRecord.setTimeStamp(new Date());
        chatRecord.setRecord("hello gdut");
        List<ChatRecord> chatRecordList= daoFactory.getChatRecordDao().getRecord(1,2);
        System.out.println(chatRecordList.size());
        daoFactory.getSqlSession().commit();
    }
}
