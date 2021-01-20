package com.example.demo.MyController;


import com.example.demo.MyHandler.SpringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class WebSocketController {

    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    @RequestMapping("/websocket/toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/login.html";
    }

    @RequestMapping("/websocket/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/websocket.html";
    }

    @RequestMapping("/websocket/send")
    @ResponseBody
    public String send(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (StringUtils.isEmpty(username)){
            infoHandler().sendMessageToUsers(new TextMessage("给所有用户发消息"));
        }else{
            infoHandler().sendMessageToUser(username, new TextMessage("给"+username+"用户发消息"));
        }
        return null;
    }

}