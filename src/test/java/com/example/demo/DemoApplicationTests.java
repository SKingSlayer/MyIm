package com.example.demo;

import com.example.demo.MyData.Entity.TmpUser;
import com.example.demo.MyTest.DataSourceTest.User1Dao;
import com.example.demo.MyTest.DataSourceTest.User2Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {
	@Resource
	RedisTemplate<String, TmpUser> redisTemplate;

//    @Test
//	public  void test1(){
//    	TmpUser tmpUser=new TmpUser();
//    	tmpUser.setCode("111");
//    	tmpUser.setEmail("111");
//    	tmpUser.setPassword("11");
//    	tmpUser.setUsername("1111");
//		redisTemplate.opsForHash().put("TmpUser","111",tmpUser);;
//		TmpUser tmp= (TmpUser)redisTemplate.opsForHash().get("TmpUser", "111");
//		System.out.println(tmp.getCode());
//	}
@Autowired
static   private User1Dao user1Dao;
	@Autowired
	static  private User2Dao user2Dao;

	public static  void main(String[] s) throws IOException {
		System.out.println(user1Dao.queryUserById(1));
		System.out.println(user1Dao.queryUserById(2));

	}

}
