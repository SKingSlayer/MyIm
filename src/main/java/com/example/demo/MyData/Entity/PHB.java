package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class PHB {
    int id;
    int senderId;
    int receiverId;
    int money;
    Date timeStamp;
}
