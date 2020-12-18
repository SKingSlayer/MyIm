package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class DemoApplicationTests {
     @Autowired
	 RedisTemplate<String, String> redisTemplate;
    @Test
	public  void test(){
		redisTemplate.opsForList().leftPush("1","2");
		redisTemplate.opsForList().leftPush("1","3");
		System.out.println(redisTemplate.opsForList().range("1",0,-1));
	}

}
