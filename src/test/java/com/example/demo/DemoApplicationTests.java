package com.example.demo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {
	@Resource
	RedisTemplate<String, String> redisTemplate;

    @Test
	public  void test1(){
    redisTemplate.opsForList().rightPush("testlist","hello","1111");
    ;
		System.out.println(redisTemplate.opsForList().leftPop("testlist"));
	}
//@Autowired
//static   private User1Dao user1Dao;
//	@Autowired
//	static  private User2Dao user2Dao;
//
//	public static  void main(String[] s) throws IOException {
//		System.out.println(user1Dao.queryUserById(1));
//		System.out.println(user1Dao.queryUserById(2));
//
//	}

}
