package com.example.demo.MyTest.DataSourceTest;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 2735011165270709366L;

    private int id;

    private String name;

    private int age;
}