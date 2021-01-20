package com.example.demo.MyHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Dao.*;
import com.example.demo.MyData.Entity.*;
import com.example.demo.utils.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import javax.xml.transform.Templates;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ConcurrentHashMap<Integer, Channel> chm=new ConcurrentHashMap<>();
    public static AtomicInteger liveNumber=new AtomicInteger(0);
    GroupRecordDao groupRecordDao;
    MemberDao memberDao;
    ObjectMapper objectMapper;
    GHBDao ghbDao;
    DaoFactory daoFactory;
    PersonalRecordDao personalRecordDao;
    PHBDao phbDao;
    FriendDao friendDao;
    AliveUserDao aliveUserDao;

    RedisTemplate<String,String> redisTemplate;
//    @Autowired
//    RedisTemplate<int,>



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");
        liveNumber.getAndIncrement();
        System.out.println("线上的用户"+liveNumber.get());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");
        //添加到channelGroup 通道组
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());
        liveNumber.compareAndSet(liveNumber.get(),liveNumber.get()-1);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        aliveUserDao=(AliveUserDao) SpringUtil.getBean("aliveUserDao");
         daoFactory=(DaoFactory) SpringUtil.getBean("DaoFactory");
        memberDao=(MemberDao) SpringUtil.getBean("MemberDao");
        phbDao=(PHBDao) SpringUtil.getBean("PHBDao") ;
        objectMapper=new ObjectMapper();
        friendDao=(FriendDao) SpringUtil.getBean("friendDao");
        groupRecordDao=(GroupRecordDao) SpringUtil.getBean("GroupRecordDao");
        personalRecordDao=(PersonalRecordDao) SpringUtil.getBean("PersonalRecordDao");
        ghbDao=(GHBDao) SpringUtil.getBean("GHBDao");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(fmt);
        redisTemplate= (RedisTemplate<String, String>) SpringUtil.getBean("redisTemplate");
        redisTemplate.opsForSet().add("hlllo","11");
        log.info("m1111111111111111111111111y");
        log.info(String.valueOf(redisTemplate.opsForSet().members("hlllo").size()));
        //首次连接是FullHttpRequest，处理参数 by zhengkai.blog.csdn.net
//        if (msg instanceof FullHttpRequest) {
//            FullHttpRequest request = (FullHttpRequest) msg;
//            String uri = request.uri();
//
//            System.out.println("接收到的参数是："+ "hello");
//            //如果url包含参数，需要处理
//            if(uri.contains("?")){
//                String newUri=uri.substring(0,uri.indexOf("?"));
//                System.out.println(newUri);
//                request.setUri(newUri);
//            }
//
//        }else
        if (null != message && message instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) message;
            String uri = request.uri();

            Map paramMap = getUrlParams(uri);
            System.out.println("接收到的参数是：" + JSON.toJSONString(paramMap));
            //如果url包含参数，需要处理
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }
            System.out.println(paramMap.get("friendId"));
        }

         if(message instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)message;
            System.out.println("客户端收到服务器数据：" +frame.text());
             String msg = frame.text();
             Pattern pi=Pattern.compile("^#init");
             Matcher mi=pi.matcher(msg);

             if(mi.find()){
                 int userId=Integer.parseInt(msg.replaceAll("^#init",""));
                 System.out.println(userId);
                 if(aliveUserDao.getAliveUserById(userId)==null)
                 aliveUserDao.addAliveUser(userId,new Date());
                 chm.put(userId, ctx.channel());
                 chm.get(userId).writeAndFlush(new TextWebSocketFrame("hello world"));
             }
             Pattern ph=Pattern.compile("^hello");
             System.out.println(msg);
             Matcher mh=ph.matcher(msg);
             if(mh.find()){
                 msg=msg.replaceAll("^hello","");
                 ctx.channel().writeAndFlush(new TextWebSocketFrame("hello lihuan"));//字符串不好使，要封装的
             }
             Pattern pg=Pattern.compile("^#getAF");
             Matcher mg=pg.matcher(msg);
             if(mg.find()){ //注意不要串了
                 String userId=msg.replaceAll("^#getAF","");
                 System.out.println("anyway"+userId);
                 List<Friend> friends= daoFactory.getFriendDao().getFriendList(Integer.parseInt(userId));
                 for(Friend friend:friends){
                     if(chm.get(friend.getFriendId())!=null)
                         friend.setIsAlive(1);
                 }
                 ctx.channel().writeAndFlush(new TextWebSocketFrame("#getAF"+objectMapper.writeValueAsString(friends)));
             }

             Pattern p6=Pattern.compile("^#group");
             Matcher m6=p6.matcher(msg);
             if(m6.find()){
                 String tmp=msg.replaceAll("^#group","");
                 doGroupMsg(tmp,ctx);  //注意是tmp，不是msg。
//                 doGHB(tmp,ctx);
             }

             Pattern p7=Pattern.compile("^#personal");
             Matcher m7=p7.matcher(msg);
             if(m7.find()){
                 String tmp=msg.replaceAll("^#personal","");
                 doPersonalMsg(tmp,ctx);
             }

        }
        super.channelRead(ctx, message);
    }
    private void doPersonalMsg(String msg, ChannelHandlerContext ctx) throws JsonProcessingException {
        Pattern p2=Pattern.compile(("#msg1")); //有#号
        Matcher m2=p2.matcher(msg);
        if(m2.find()){
            msg=msg.replaceAll("#msg1","");//有#号
            doPersonalMsg2(msg,ctx);
        }
        Pattern p4=Pattern.compile(("#msg3")); //有#号
        Matcher m4=p4.matcher(msg);
        if(m4.find()){
            msg=msg.replaceAll("#msg3","");//有#号
            doPersonalMsg4(msg,ctx);
        }
    }
    private void doPersonalMsg2(String msg,ChannelHandlerContext ctx) throws JsonProcessingException {
         PersonalRecord personalRecord= objectMapper.readValue(msg,PersonalRecord.class);
         personalRecordDao.addRecord(personalRecord);
         friendDao.updateUMSG(personalRecord.userId,personalRecord.friendId);
        friendDao.updateUMSG(personalRecord.friendId,personalRecord.userId);
         String record=personalRecord.getRecord();
        Pattern p1=Pattern.compile("#phb");
        Matcher m1=p1.matcher(record);
        if(m1.find()){
            String s=record.replaceAll("#phb","");
            PHB phb=objectMapper.readValue(s,PHB.class);
            synchronized (this){
                phbDao.addPHB(phb);
                phb= phbDao.getPHB(phbDao.getLastId());//可能会有其他线程添加了
            }
            personalRecord.setRecord("#phb"+objectMapper.writeValueAsString(phb));
            msg=objectMapper.writeValueAsString(personalRecord);
        }

        if(chm.get(personalRecord.getUserId())!=null){
            log.info(String.valueOf(personalRecord.getUserId()));
            chm.get(personalRecord.getUserId()).writeAndFlush(new TextWebSocketFrame("#personal#msg2"+msg));
        }
        if(chm.get(personalRecord.getFriendId())!=null){
            log.info(String.valueOf(personalRecord.getFriendId()));
            chm.get(personalRecord.getFriendId()).writeAndFlush(new TextWebSocketFrame("#personal#msg2"+msg));
        }

         log.info("personal"+personalRecord.getRecord());
    }

    private void doPersonalMsg4(String msg,ChannelHandlerContext ctx) throws JsonProcessingException {
        PersonalRecord personalRecord= objectMapper.readValue(msg,PersonalRecord.class);
        friendDao.clearUMSG(personalRecord.userId,personalRecord.friendId);
        friendDao.updateTimeStamp(personalRecord.userId,personalRecord.friendId,personalRecord.getTimeStamp());
        log.info(msg+"msg4");
//        int groupId=personalRecord.getGroupId();//获取群ID
//        int userId=groupRecord.getUserId();
//        memberDao.clearUMSG(userId,groupId);
//        memberDao.updateTimeStamp(userId,groupId,groupRecord.getTimeStamp());
    }



    private void doGroupMsg(String msg,ChannelHandlerContext ctx) throws JsonProcessingException {
            Pattern p2=Pattern.compile(("#msg1")); //有#号
            Matcher m2=p2.matcher(msg);
            if(m2.find()){
                msg=msg.replaceAll("#msg1","");//有#号
                doGroupMsg2(msg,ctx);
            }
        Pattern p4=Pattern.compile(("#msg3")); //有#号
        Matcher m4=p4.matcher(msg);
        if(m4.find()){
            msg=msg.replaceAll("#msg3","");//有#号
            doGroupMsg4(msg,ctx);
        }
    }
    void doGroupMsg2(String msg,ChannelHandlerContext ctx) throws JsonProcessingException {

        //此时msg还有#ghb直接存进record里
        GroupRecord groupRecord= objectMapper.readValue(msg, GroupRecord.class);
        int groupId=groupRecord.getGroupId();//获取群ID
        int userId=groupRecord.getUserId();
        String record=groupRecord.getRecord();
        groupRecordDao.addGroupRecord(groupRecord);//2.存储record
        Pattern p1=Pattern.compile("#ghb");
        Matcher m1=p1.matcher(record);
        if(m1.find()){
            String s=record.replaceAll("#ghb","");
            GHB ghb=objectMapper.readValue(s,GHB.class);//这里可能会有引用导致的问题，是引用就不行
            GHB ghb1=new GHB();//可以看出是引用类型
            ghb1.setMoney(Math.floor(ghb.getMoney()/2));
            GHB ghb2=new GHB();
            ghb2.setMoney(ghb.getMoney()-(Math.floor(ghb.getMoney()/2)));
            ghbDao.addGHB1(ghb1);
            ghbDao.addGHB2(ghb2);
        }
        List<Member> members=memberDao.getAllMember(groupId);;
        System.out.println(members.size());
        for (Member member : members) {

            memberDao.updateUMSG(member.getUserId(),member.getGroupId());


            if (chm.get(member.getUserId()) != null) {
                System.out.println(member.getUserId());
                chm.get(member.getUserId()).writeAndFlush(new TextWebSocketFrame("#group#msg2" + msg));//3.发送消息
            }
        }
    }

    void doGroupMsg4(String msg,ChannelHandlerContext ctx) throws JsonProcessingException {
        GroupRecord groupRecord= objectMapper.readValue(msg, GroupRecord.class);
        int groupId=groupRecord.getGroupId();//获取群ID
        int userId=groupRecord.getUserId();
        memberDao.clearUMSG(userId,groupId);
        memberDao.updateTimeStamp(userId,groupId,groupRecord.getTimeStamp());
    }//如果消息丢了，可能再看到消息，不过可能性不大

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    private void sendAllMessage(String message){
        //收到信息后，群发给所有channel
        MyChannelHandlerPool.channelGroup.writeAndFlush( new TextWebSocketFrame(message));
    }

    private static Map getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }
        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
            }
            return  map;

        }else{
            return map;
        }
    }














}