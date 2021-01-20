package com.example.demo.MyController;


import com.example.demo.MyData.Dao.PHBDao;
import com.example.demo.MyData.Dao.PersonalRecordDao;
import com.example.demo.MyData.Dao.UserDao;
import com.example.demo.MyData.Entity.PHB;
import com.example.demo.MyData.Entity.PersonalRecord;
import com.example.demo.MyData.Entity.TmpUser;
import com.example.demo.MyData.Entity.User;
import com.example.demo.MyImpl.UserServiceImpl;
import com.example.demo.utils.UUIDUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/user")
@Api(value = "用户")
public class UserController {
    @Autowired
    PHBDao phbDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    PersonalRecordDao personalRecordDao;
    @RequestMapping(method = RequestMethod.POST,value = "/userById")
    @ApiOperation(value = "获取用户信息", notes = "通过用户ID获取用户信息")
    public Object findById(@ApiParam(value = "用户ID",required = true) int id){
        return userDao.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/userByName")
    @ApiOperation(value = "获取用户信息", notes = "通过用户姓名获取用户信息")
    public Object findByName(@ApiParam(value = "用户姓名",required = true) String  name){
        return userDao.findByName(name);
    }
    @ResponseBody
    @GetMapping("/record/search")
    String getRecordByFullIndex(@RequestParam("userId")int userId,@RequestParam("friendId")int friendId,@RequestParam("record") String record) throws JsonProcessingException {
        List<PersonalRecord> personalRecords=  personalRecordDao.getRecordByFullIndex(userId,friendId,record);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValueAsString(personalRecords);
        return objectMapper.writeValueAsString(personalRecords);
    }
    @GetMapping("/login")
    public String login1(){
        return "mylogin";
    }
    @RequestMapping("/checking")
    public String myLogin(@RequestParam("username") String username,@RequestParam("password") String password, HttpServletResponse response, HttpServletRequest request){
            User user=userDao.getUserByName(username);
        if(user.getPassword().equals(password))
        {

            log.info("hello");
            request.getSession().setAttribute("username","password");
            return "redirect:/user/chatUI?userId="+user.getUserId()+"&username="+username;
        }
        else
            return "mylogin";


    }
    @GetMapping("/chatUI")
    public String chatUI(@RequestParam("username") String username,@RequestParam("userId") String userId, Model model){
        model.addAttribute("userId",userId);
        model.addAttribute("username",username);
        return "chatUI";
    }
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username")String username, @RequestParam("password") String password ,@RequestParam("email") String email){
        String code = UUIDUtils.getUUID()+ UUIDUtils.getUUID();
        TmpUser user=new TmpUser();
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCode(code);
        userServiceImpl.register(user);
        System.out.println(username);
        return "having send";
    }
    @ResponseBody
    @GetMapping("/checkcode")
    public String checkCode(@RequestParam("code") String code){
        log.info("hello world");
        TmpUser tmpUser= userServiceImpl.checkCode(code);
        User user=new User();
        if(tmpUser!=null){
            user.setUsername(tmpUser.getUserName());
            user.setPassword(tmpUser.getPassword());
            user.setEmail(tmpUser.getEmail());
            user.setMoney(0);
            userDao.registerUser(user);
        }

        return "激活成功";
    }
    @Transactional
    @ResponseBody
    @GetMapping("/phb/grab")
    public String grabPHB(@RequestParam("id") int id, @RequestParam("userId")int userId, HttpResponse httpResponse) throws JsonProcessingException {
       PHB phb=phbDao.getPHB(id);
       userDao.addMoney(userId,phb.getMoney());
       phbDao.deletePHB(id);
       User user= userDao.getUser(userId);
       ObjectMapper objectMapper=new ObjectMapper();
        return "#success"+objectMapper.writeValueAsString(user);
    }


}