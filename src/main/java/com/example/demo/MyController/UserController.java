package com.example.demo.MyController;


import com.example.demo.MyData.Dao.UserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(value = "用户信息管理")
public class UserController {
    UserDao userDao;

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

}