package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class GHB {
    int id;
    int userId;
    int groupId;
    Double money;
    int size;
    Date timeStamp;
}
