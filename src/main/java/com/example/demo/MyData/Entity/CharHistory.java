package com.example.demo.MyData.Entity;


import java.util.Date;

public class CharHistory {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public int getRecv() {
        return recv;
    }

    public void setRecv(int recv) {
        this.recv = recv;
    }

    public Date getMyTime() {
        return myTime;
    }

    public void setMyTime(Date myTime) {
        this.myTime = myTime;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int id;
    public int send;
    public int recv;
    public Date myTime;
    public String history;

}
