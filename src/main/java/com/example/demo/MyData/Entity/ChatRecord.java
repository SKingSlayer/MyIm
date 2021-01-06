package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRecord {

    public int id;
    public int userId;
    public int friendId;
    public String Record;
    public Date timeStamp;


}
