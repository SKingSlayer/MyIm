package com.example.demo.MyController;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyConfig.SwaggerConfig;
import com.example.demo.MyData.Dao.*;
import com.example.demo.MyData.Entity.AliveUser;
import com.example.demo.MyData.Entity.GHB;
import com.example.demo.RocketMq.Demo.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.internal.org.objectweb.asm.Handle;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import static com.example.demo.MyHandler.MyWebSocketHandler.chm;

@Api(value = "/group",tags = {SwaggerConfig.TAG_1})
@Controller
@RequestMapping("/group")
@Slf4j
public class GroupController {
    static Producer producer;

    static {
        try {
            producer = new Producer();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    MemberDao memberDao;
    @Autowired
    GHBDao ghbDao;
    @Autowired
    UserDao userDao;
    ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    AliveUserDao aliveUserDao;
    @Autowired
    FriendDao friendDao;

    public GroupController() throws MQClientException {

    }
@ApiOperation("获得群组列表")
    @ResponseBody
    @GetMapping("ag")
    public String getAllGroup(@RequestParam("userId") int userId) throws IOException {
        DaoFactory daoFactory=new DaoFactory();
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(memberDao.getAllGroup(userId));
    }
    @ResponseBody
    @ApiOperation("获得朋友列表")
    @GetMapping("af")
    public String getAllFriend(@RequestParam("userId") int userId) throws IOException {
        DaoFactory daoFactory=new DaoFactory();
        ObjectMapper objectMapper=new ObjectMapper();
            if(aliveUserDao.getAliveUserById(userId)==null)
            {
                aliveUserDao.addAliveUser(userId,new Date());
            }
        else
        aliveUserDao.upDateAliveUser(userId,new Date());
        return objectMapper.writeValueAsString(friendDao.getFriendList(userId));
    }
    @ApiOperation(value = "抢红包接口", tags = {"grab red envelopes"},response =String.class)
    @ResponseBody
    @GetMapping("grabghb")
    public  String grabGHB(@RequestParam("id") @ApiParam(value = "id of grab red envelopes" ,required = true) int id, @RequestParam("userId") @ApiParam(value = "id of receiver" ,required = true) int userId){
        GHB ghb= ghbDao.getGHB(id);
        log.info("id"+id);
        if(ghb==null)
            return null;
        if(ghb.getSize()==1){
            ghbDao.subMoney1(id,ghb.getMoney(),ghb.getMoney()); //只有一份就全给他
        }
        Random r  = new Random();
        double max=ghb.getMoney()/ghb.getSize();
        double num = r.nextDouble() * max;
        num = Math.floor(num * 100) / 100;
        ghbDao.subMoney1(id,ghb.getMoney(),num);
        return "success";
    }
    @ResponseBody
    @GetMapping("test1")
    void test1(@RequestParam("id") int id, @RequestParam("userId")int userId) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        log.info(String.valueOf(userId));
        producer.sendMessage(String.valueOf(userId));
        return ;
    }
    @ResponseBody
    @GetMapping("test2")
    @CrossOrigin(value = "http://localhost:63342",allowedHeaders = "*",maxAge = 1800)
    String test2(@RequestParam("id") int id, @RequestParam("userId")int userId) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        log.info(String.valueOf(userId));
        return "hello world";
    }

    @ResponseBody
    @GetMapping("test3")
    String test3(@RequestParam("id") int id, @RequestParam("userId")int userId) throws MQClientException, InterruptedException, RemotingException, MQBrokerException, JsonProcessingException {
        GHB ghb=new GHB();
        ghb.setId(id);
        ghb.setUserId(userId);
        ghb.setMoney(10.0);
        producer.sendMessage(objectMapper.writeValueAsString(ghb));
        return "groupmsg";
    }
    @GetMapping("test4")
    String test4(@RequestParam("userId")int userId, Model model) throws MQClientException, InterruptedException, RemotingException, MQBrokerException, IOException {
        model.addAttribute("userId","2");
        model.addAttribute("username","xiaohong");
        return "groupmsg";
    }
    @GetMapping("test5")
    String test5(@RequestParam("id") int id, @RequestParam("userId")int userId) throws MQClientException, InterruptedException, RemotingException, MQBrokerException, IOException {
        return "xiaohong";
    }



}
