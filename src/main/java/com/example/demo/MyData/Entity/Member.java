package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class Member {
    int userId;
    int groupId;
    String userName;
    String groupName;
    int umsg;
    Date timeStamp;
}
