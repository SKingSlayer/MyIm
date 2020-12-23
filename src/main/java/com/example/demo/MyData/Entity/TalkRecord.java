package com.example.demo.MyData.Entity;


import lombok.Data;

import java.util.Date;
@Data
public class TalkRecord {


    public int userId;
    public int friendId;
    public Date myTime;
    public String record;

}
