package com.example.demo.MyData.Config;

import com.example.demo.MyConfig.MyTest;
import com.example.demo.MyData.Dao.*;
import com.example.demo.MyData.Entity.AliveUser;
import com.example.demo.MyData.Entity.GHB;
import com.example.demo.MyData.Entity.GroupRecord;
import com.example.demo.utils.AliveUserThread;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class Test2 {
    @Autowired
    MemberDao memberDao;

    @Autowired
    GroupRecordDao groupRecordDao;
    @Autowired
    GHBDao ghbDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AliveUserThread aliveUserThread;
    @Autowired
    AliveUserDao aliveUserDao;
    @PostConstruct
    void hello() throws JsonProcessingException, InterruptedException {
        AliveUser aliveUser=aliveUserDao.getAliveUserById(2);
        log.info(String.valueOf(aliveUser==null));
        aliveUserThread.updateAliveUser();
        ObjectMapper objectMapper=new ObjectMapper();
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
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date=new Date();
        long j=date.getTime()-2*24*3600*1000;
        log.info(simpleDateFormat.format(new Date(j)));
        log.info(String.valueOf(userDao.getMoney(1)));
        userDao.setMoney(1,0);
        ghbDao.setMoney(1,5000);
    }
}
