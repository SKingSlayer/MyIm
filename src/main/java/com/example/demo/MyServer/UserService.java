package com.example.demo.MyServer;

import com.example.demo.MyData.Entity.TmpUser;
import com.example.demo.MyData.Entity.User;

public interface UserService {
    /**
     * 用户注册,
     * @param user
     */
    void register(TmpUser user);

    /**
     * 根据激活码code查询用户，之后再进行修改状态
     * @param code
     * @return
     */
   TmpUser checkCode(String code);


}