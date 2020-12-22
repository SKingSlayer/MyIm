package com.example.demo.MyConfig;


import com.example.demo.MyHandler.FriendsAquiringHandler;
import com.example.demo.MyHandler.SpringWebSocketHandler;
import com.example.demo.MyInterceptor.FriendsAcquiringHandlerInterceptor;
import com.example.demo.MyInterceptor.SpringWebSocketHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class SpringWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(webSocketHandler(),"/websocket/socketServer.do").setAllowedOrigins("*")
                .addInterceptors(new SpringWebSocketHandlerInterceptor());

        registry.addHandler(webSocketHandler(), "/sockjs/socketServer.do").setAllowedOrigins("*")
                .addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
        try {
            registry.addHandler(new FriendsAquiringHandler(),"/websocket/getFriends").setAllowedOrigins("*")
                    .addInterceptors(new FriendsAcquiringHandlerInterceptor());//不要写成ws://localhost:8081/websocket/getFriends
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Bean
    public TextWebSocketHandler webSocketHandler(){

        return new SpringWebSocketHandler();
    }

}