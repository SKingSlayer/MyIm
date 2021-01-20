package com.example.demo.MyController;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Dao.GroupDao;
import com.example.demo.MyData.Dao.MemberDao;
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
    GroupDao groupDao;
    @Autowired
    MemberDao memberDao;

    @GetMapping("/")
    public String  returnLogin(){

        return "login";
    }


    @GetMapping("/ts")
    public String  ts(){

        return "index";
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







}