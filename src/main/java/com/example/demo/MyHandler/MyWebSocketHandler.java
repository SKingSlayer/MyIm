package com.example.demo.MyHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Entity.ChatRecord;
import com.example.demo.MyData.Entity.Friend;
import com.example.demo.MyData.Entity.PHB;
import com.example.demo.MyData.Entity.TalkMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ConcurrentHashMap<Integer, Channel> chm=new ConcurrentHashMap<>();
    public static AtomicInteger liveNumber=new AtomicInteger(0);



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
    @Transactional
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        DaoFactory daoFactory=new DaoFactory();
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


        ObjectMapper objectMapper=new ObjectMapper();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(fmt);
         if(message instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)message;
            System.out.println("客户端收到服务器数据：" +frame.text());
             String msg = frame.text();
             Pattern pi=Pattern.compile("^#init");
             Matcher mi=pi.matcher(msg);

             if(mi.find()){
                 System.out.println("flag");
                 int userId=Integer.parseInt(msg.replaceAll("^#init",""));
                 System.out.println(userId);
                 if(daoFactory.getAliveUserDao().getAliveUser(userId)==null)
                     daoFactory.getAliveUserDao().addAliveUser(userId,new Date());
                 else
                     daoFactory.getAliveUserDao().upDateAliveUser(userId,new Date());
                 daoFactory.getSqlSession().commit();
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
             Pattern pb=Pattern.compile("^#phb1");
             Matcher mhb=pb.matcher(msg);
             if(mhb.find()){
                 String tmp=msg.replaceAll("^#phb1","");
                 PHB phb=objectMapper.readValue(tmp,PHB.class);
                 daoFactory.getUserDao().reduceMoney(phb.getSenderId(),phb.getMoney());
                 daoFactory.getPhbDao().addPHB(phb);
                 phb.setId(daoFactory.getPhbDao().getLastId());
                 ChatRecord chatRecord=new ChatRecord();
                 String s=objectMapper.writeValueAsString(phb);//红包信息 userId friendId id
                 s="#phb6"+s;
                 chatRecord.setRecord(s);//聊天记录中的红包：phb6+红包
                 chatRecord.setUserId(phb.getSenderId());
                 chatRecord.setFriendId(phb.getReceiverId());
                 chatRecord.setTimeStamp(phb.getTimeStamp());
                 daoFactory.getChatRecordDao().addRecord(chatRecord);
                 daoFactory.getFriendDao().updateUMSG(chatRecord.getUserId(),chatRecord.getFriendId());
                 daoFactory.getFriendDao().updateUMSG(chatRecord.getFriendId(),chatRecord.getUserId());
                 String m=objectMapper.writeValueAsString(chatRecord);
                 if(chm.get(chatRecord.getFriendId())!=null)
                 {
                     chm.get(chatRecord.getFriendId()).writeAndFlush(new TextWebSocketFrame("#gm2"+m));//将消息发送给朋友
                 }
                 if(chm.get(chatRecord.getUserId())!=null)
                 {
                     chm.get(chatRecord.getUserId()).writeAndFlush(new TextWebSocketFrame("#gm2"+m));//将消息发送给自己
                 }
                 daoFactory.getSqlSession().commit();
                 System.out.println("success");
             }
             Pattern p3=Pattern.compile("^#phb4");
             Matcher m3=p3.matcher(msg);
             if(m3.find()){
                 String tmp=msg.replaceAll("^#phb4","");
                 PHB phb=objectMapper.readValue(tmp,PHB.class);
                 chm.get(phb.getSenderId()).writeAndFlush(new TextWebSocketFrame("#phb2"+objectMapper.writeValueAsString(phb)));
             }
             Pattern p1=Pattern.compile("^#gm1");//用于服务器处理客户端消息请求
             Matcher m1=p1.matcher(msg);
             if(m1.find()){
                 String tmp=msg.replaceAll("^#gm1","");
                 Friend friend=objectMapper.readValue(tmp,Friend.class);
                 List<ChatRecord> chatRecord= daoFactory.getChatRecordDao().getRecord(friend.getUserId(), friend.getFriendId(),daoFactory.getFriendDao().getTimeStamp(friend.getUserId(), friend.getFriendId()));
                 String s=objectMapper.writeValueAsString(chatRecord);
                 chm.get(friend.getFriendId()).writeAndFlush(new TextWebSocketFrame("#gm2"+s));//gm2用于客户端处理服务器传来的消息
             }
             Pattern p2=Pattern.compile("#cm");//标志客户端已读消息，可以更新时间戳，并将未读消息清零
             Matcher m2=p2.matcher(msg);
             if(m2.find()){
                 String tmp=msg.replaceAll("^#cm","");
                 Friend friend=objectMapper.readValue(tmp,Friend.class);
                 daoFactory.getFriendDao().updateTimeStamp(friend);//更新时间戳
                 daoFactory.getFriendDao().clearUMSG(friend.getUserId(),friend.getFriendId());
                 daoFactory.getSqlSession().commit();
                 System.out.println("success");
             }
             Pattern p4=Pattern.compile("#sm1");//客户端向服务器发送消息，服务器存储消息，更新friend_list user和friend的未读消息
             Matcher m4=p4.matcher(msg);
             if(m4.find()){
                 String tmp=msg.replaceAll("^#sm1","");
                 ChatRecord chatRecord=objectMapper.readValue(tmp,ChatRecord.class);
                 daoFactory.getChatRecordDao().addRecord(chatRecord);
                 daoFactory.getFriendDao().updateUMSG(chatRecord.getUserId(),chatRecord.getFriendId());
                 daoFactory.getFriendDao().updateUMSG(chatRecord.getFriendId(),chatRecord.getUserId());
                 String m=objectMapper.writeValueAsString(chatRecord);
                 if(chm.get(chatRecord.getFriendId())!=null)
                 {
                     chm.get(chatRecord.getFriendId()).writeAndFlush(new TextWebSocketFrame("#gm2"+m));//将消息发送给朋友
                 }
                 if(chm.get(chatRecord.getUserId())!=null)
                 {
                     chm.get(chatRecord.getUserId()).writeAndFlush(new TextWebSocketFrame("#gm2"+m));//将消息发送给自己
                 }
                 daoFactory.getSqlSession().commit();
                 System.out.println("success");
             }
             Pattern p5=Pattern.compile("^#phb7");
             Matcher m5=p5.matcher(msg);
             if(m5.find()){
                 String tmp=msg.replaceAll("^#phb7","");
                 PHB phb=objectMapper.readValue(tmp,PHB.class);
                 PHB phb1= daoFactory.getPhbDao().getPHB(phb.getId());

                     daoFactory.getUserDao().addMoney(phb.getSenderId(),phb1.getMoney());//可以用客户端传过来的红包信息 安全性方面是个问题
                     daoFactory.getPhbDao().deletePHB(phb1.getId());
                     daoFactory.getSqlSession().commit(); //抢到了要发条信息在消息数据表中，然后通知给双方
                     System.out.println(phb.getSenderId());
                     chm.get(phb.getSenderId()).writeAndFlush(new TextWebSocketFrame("抢到了"));

             }

             Pattern p6=Pattern.compile("^#msg1");
             Matcher m6=p6.matcher(msg);
             if(m6.find()){
                 String tmp=msg.replaceAll("^#msg1","");
             }

        }
        super.channelRead(ctx, message);
    }

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