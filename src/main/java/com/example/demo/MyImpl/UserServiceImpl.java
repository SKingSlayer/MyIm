package com.example.demo.MyImpl;

import com.example.demo.MyData.Entity.TmpUser;
import com.example.demo.MyData.Entity.User;
import com.example.demo.MyServer.MailService;
import com.example.demo.MyServer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    /**
     * redis接口
     */
    @Resource
    RedisTemplate<String,TmpUser> redisTemplate;

    /**
     * 注入邮件接口
     */
    @Autowired
    private MailService mailService;

    /**
     * 用户注册，同时发送一封激活邮件
     * @param user
     */
    @Override
    public void register(TmpUser user) {
        String code = user.getCode();
        System.out.println("code:"+code);
        redisTemplate.opsForHash().put("TmpUser",code,user);
        //主题
        String subject = "jihuo";
        //user/checkCode?code=code(激活码)是我们点击邮件链接之后根据激活码查询用户，如果存在说明一致，将用户状态修改为“1”激活
        //上面的激活码发送到用户注册邮箱
        String context = "<a href=\"http://localhost:8080/user/checkcode?code="+code+"\">激活请点击:"+code+"</a>";
        //发送激活邮件
        mailService.sendHtmlMail (user.getEmail(),subject,context);
    }

    /**
     * 根据激活码code进行查询用户，之后再进行修改状态
     * @param code
     * @return
     */
    @Override
    public TmpUser checkCode(String code) {
    redisTemplate.opsForHash().get("TmpUser",code);
        return (TmpUser) redisTemplate.opsForHash().get("TmpUser",code);
    }



}
