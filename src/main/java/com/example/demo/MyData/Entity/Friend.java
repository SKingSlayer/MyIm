package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class Friend {
    int userId;
    int friendId;
    String friendName;
    int isAlive;
    Date timeStamp;
    int umsg;
}
