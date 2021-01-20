package com.example.demo;

import com.example.demo.MyConfig.MyTest;
import com.example.demo.MyServer.NettyServer;

import com.example.demo.MyTest.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.ContextLoader;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	public static ThreadPoolTaskExecutor poolTaskExecutor;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("https://blog.csdn.net/moshowgame");
		System.out.println("http://127.0.0.1:6688/netty-websocket/index");
		new NettyServer(12345).start();


	}


}
