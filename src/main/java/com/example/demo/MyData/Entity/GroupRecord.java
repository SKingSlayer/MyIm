package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class GroupRecord {
    public int id;
    public int senderId;
    public int groupId;
    public String Record;
    public Date timeStamp;
}
