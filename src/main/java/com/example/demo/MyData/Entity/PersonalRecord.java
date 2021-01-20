package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class PersonalRecord {

    public int id;
    public int userId;
    public int friendId;
    public String Record;
    public Date timeStamp;


}
