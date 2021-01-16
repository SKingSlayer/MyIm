package com.example.demo.MyTest.DataSourceTest;

import com.example.demo.MyData.Dao.*;
import com.example.demo.MyData.Entity.GHB;
import com.example.demo.MyData.Entity.GroupRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
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
    @Autowired
    GHBDao ghbDao;
    @Autowired
    UserDao userDao;
    @PostConstruct
    void hello() throws JsonProcessingException {

        ObjectMapper objectMapper=new ObjectMapper();
//        System.out.println(user2Dao.queryUserById(1));
//        System.out.println(chatRecordDao.getRecordByFullIndex("hao").size());
        GroupRecord groupRecord=new GroupRecord();
        groupRecord.setGroupId(1);
        groupRecord.setRecord("hello");
        groupRecord.setUserId(2);
        groupRecord.setTimeStamp(new Date());
        groupRecordDao.addGroupRecord(groupRecord);
        groupRecordDao.getGroupRecord(1,new Date());
        memberDao.updateTimeStamp(1,1,new Date());
        memberDao.updateUMSG(1,1);
        memberDao.clearUMSG(1,1);
        GHB ghb=ghbDao.getGHB(1);
        ghbDao.addGHB(ghb);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date=new Date();
        long j=date.getTime()-2*24*3600*1000;
        log.info(simpleDateFormat.format(new Date(j)));
        log.info(String.valueOf(userDao.getMoney(1)));
        userDao.setMoney(1,0);
        ghbDao.setMoney(1,5000);
    }
}
