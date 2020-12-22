package com.example.demo.MyData.Entity;

import lombok.Data;

@Data
public class Rm {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRmNum() {
        return rmNum;
    }

    public void setRmNum(int rmNum) {
        this.rmNum = rmNum;
    }

    int id;
    int rmNum;
}
