package com.example.demo.MyController;

import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.MyData.Dao.MemberDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class GroupController {
    @Autowired
    MemberDao memberDao;

    @ResponseBody
    @GetMapping("ag")
    public String getAllGroup(@RequestParam("userId") int userId) throws IOException {
        DaoFactory daoFactory=new DaoFactory();
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(memberDao.getAllGroup(userId));
    }
    @ResponseBody
    @GetMapping("af")
    public String getAllFriend(@RequestParam("userId") int userId) throws IOException {
        DaoFactory daoFactory=new DaoFactory();
        ObjectMapper objectMapper=new ObjectMapper();
        daoFactory.getFriendDao().getFriendList(userId);
        return objectMapper.writeValueAsString(daoFactory.getFriendDao().getFriendList(userId));
    }
}
