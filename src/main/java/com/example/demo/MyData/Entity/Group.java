package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class Group {
    int groupId;
    String groupName;
    int membership;
    Date createTime;
}
