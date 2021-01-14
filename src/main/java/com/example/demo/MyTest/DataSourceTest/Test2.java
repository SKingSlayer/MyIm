package com.example.demo.MyTest.DataSourceTest;

import com.example.demo.MyData.Dao.ChatRecordDao;
import com.example.demo.MyData.Dao.GroupRecordDao;
import com.example.demo.MyData.Dao.MemberDao;
import com.example.demo.MyData.Entity.GroupRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class Test2 {
    @Autowired
    MemberDao memberDao;
    @Autowired
    User2Dao user2Dao;
    @Autowired
    User1Dao user1Dao;
    @Autowired
    ChatRecordDao
    chatRecordDao;
    @Autowired
    GroupRecordDao groupRecordDao;
    @PostConstruct
    void hello() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
    System.out.println(user1Dao.queryUserById(1));
//        System.out.println(user2Dao.queryUserById(1));
//        System.out.println(chatRecordDao.getRecordByFullIndex("hao").size());
        GroupRecord groupRecord=new GroupRecord();
        groupRecord.setGroupId(1);
        groupRecord.setRecord("11");
        groupRecord.setUserId(2);
        groupRecord.setTimeStamp(new Date());
       groupRecordDao.addGroupRecord(groupRecord);
        groupRecordDao.getGroupRecord(1,new Date());
        System.out.println(objectMapper.writeValueAsString(memberDao.getAllGroup(1)));
    }
}
