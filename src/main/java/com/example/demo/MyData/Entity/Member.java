package com.example.demo.MyData.Entity;

import lombok.Data;

@Data
public class Member {
    int userId;
    int groupId;
    String userName;
    String groupName;
    int umsg;
    int timeStamp;
}
