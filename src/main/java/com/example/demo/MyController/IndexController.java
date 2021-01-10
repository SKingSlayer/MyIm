package com.example.demo.MyController;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Entity.Friend;
import com.example.demo.MyData.Entity.TmpUser;
import com.example.demo.MyImpl.UserServiceImpl;
import com.example.demo.MyServer.UserService;
import com.example.demo.utils.UUIDUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @GetMapping("/")
    public String  returnLogin(){

        return "login";
    }


    @GetMapping("/ts")
    public String  ts(){

        return "index";
    }
    @GetMapping("/login")
    public String MyLogin(){

        return "login";
    }

    @GetMapping("/index")
    public String index(){

        return "index";
    }
    @GetMapping("/socket")
    public String socket(Model model){
        model.addAttribute("userId","1");
        model.addAttribute("username","lihuan");
        return "socket";
    }
    @GetMapping("/xiaohong")
    public String xiaoong(Model model){
        model.addAttribute("userId","2");
        model.addAttribute("username","xiaohong");
        return "xiaohong";
    }
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username")String username,@RequestParam("userId") String userId
    ,@RequestParam("password") String password ,@RequestParam("email") String email){
        String code = UUIDUtils.getUUID()+ UUIDUtils.getUUID();
        TmpUser user=new TmpUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCode(code);
        user.setUserId(Integer.parseInt(userId));
        userServiceImpl.register(user);
        System.out.println(username);
        return "having send";
    }
    @ResponseBody
    @GetMapping("/checkcode")
    public String checkCode(@RequestParam("code") String code){
        TmpUser user= userServiceImpl.checkCode(code);
        System.out.println("test");
        System.out.println(user.getEmail());
        return "激活成功";
    }



}