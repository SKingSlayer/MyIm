package com.example.demo.MyInterceptor;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        System.out.println("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;


                //使用userName区分WebSocketHandler，以便定向发送消息

               String rmId = (String) servletRequest.getServletRequest().getParameter("RMID");  //一般直接保存user实体
               String userID=(String) servletRequest.getServletRequest().getParameter("USERID");
                System.out.println(rmId);
            System.out.println("UserID"+userID);
                if (rmId !=null) {
                    attributes.put("RMID",rmId);

                  }
            if (userID !=null) {
                attributes.put("USERID",userID);

            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
