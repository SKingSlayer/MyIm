package com.example.demo;

import com.example.demo.MyData.Entity.TmpUser;
import com.example.demo.RocketMq.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
	@Resource
	RedisTemplate<String, TmpUser> redisTemplate;

    @Test
	public  void test1(){
    	TmpUser tmpUser=new TmpUser();
    	tmpUser.setCode("111");
    	tmpUser.setEmail("111");
    	tmpUser.setPassword("11");
    	tmpUser.setUsername("1111");
		redisTemplate.opsForHash().put("TmpUser","111",tmpUser);;
		TmpUser tmp= (TmpUser)redisTemplate.opsForHash().get("TmpUser", "111");
		System.out.println(tmp.getCode());
	}

}
