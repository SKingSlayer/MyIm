package com.example.demo.MyData.Entity;


import lombok.Data;

import java.util.Date;
@Data
public class TalkMessage {


    public int userId;
    public int friendId;
    public Date talkTime;
    public String message;

}
