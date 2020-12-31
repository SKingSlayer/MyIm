package com.example.demo.MyData.Entity;

import lombok.Data;

import java.util.List;
@Data
public class User {
    public int userId;
    public  int money;
    public String username;
    private Integer status;
    private String  code;
    public String userEmail;


}
