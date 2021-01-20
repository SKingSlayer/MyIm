package com.example.demo.utils;

import com.example.demo.MyData.Dao.AliveUserDao;
import com.example.demo.MyData.Entity.AliveUser;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.demo.MyHandler.MyWebSocketHandler.chm;
@Slf4j
@Service
public class AliveUserThread {
    @Autowired
    AliveUserDao aliveUserDao;

    @Async
  public void  updateAliveUser() throws InterruptedException {
        while (true){

            Thread.sleep(10000);
            List<AliveUser> aliveUsers= aliveUserDao.getAliveUser();
            Date now =new Date();
            for(AliveUser aliveUser : aliveUsers){
                System.out.println("hello");
                if((now.getTime()- aliveUser.getTimeStamp().getTime()) >180)
                {
                    aliveUserDao.deleteAliveUserBy(aliveUser.getUserId());

//                    for (Map.Entry<Integer, Channel> item : chm.entrySet()){
//                        chm.remove(aliveUser.getUserId());
//                        log.info(aliveUser.getUserId()+"已下线,"+"移除"+aliveUser.getUserId());
//                    }
                }
            }


        }
        }

}
