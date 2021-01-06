package com.example.demo;

import com.example.demo.MyServer.NettyServer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class DemoApplication {
	@Autowired
	public static ThreadPoolTaskExecutor poolTaskExecutor;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
		new NettyServer(12345).start();
		System.out.println("https://blog.csdn.net/moshowgame");
		System.out.println("http://127.0.0.1:6688/netty-websocket/index");

	}


}
