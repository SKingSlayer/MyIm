package com.example.demo.MyController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class  TestController {

    @RequestMapping(value = "/successLogin", method=RequestMethod.GET)
    public String successLogin() { return "successLogin"; }

    @ResponseBody
    @CrossOrigin(value = "http://localhost:8080",allowedHeaders = "*",maxAge = 1800)
    @RequestMapping(value = "/selectUserName", method = RequestMethod.POST)
    public String selectUserName(@RequestBody User user) {
        String userName = user.getUserName();
        String userPassword = user.getUserPassword();
        System.out.println(userName + userPassword);

        String result = "-1";


        //用户不存在
        if (userName.equals("li")&&userPassword.equals("123456")) {
//            return "用户不存在";
            result = "2";

        }
        return result;
    }
}