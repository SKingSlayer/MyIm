package com.example.demo.MyInterceptor;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class FriendsAcquiringHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//
//            String username = (String) servletRequest.getServletRequest().getParameter("username");  //一般直接保存user实体
//            String friendName=(String) servletRequest.getServletRequest().getParameter("friendName");
//            System.out.println(username);
//            System.out.println("UserID "+friendName);
//            if (username !=null) {
//                attributes.put("RMID",username);
//
//            }
//            if (friendName !=null) {
//                attributes.put("USERID",friendName);
//
//            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
