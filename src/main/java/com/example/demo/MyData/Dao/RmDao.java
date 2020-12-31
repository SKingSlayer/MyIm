package com.example.demo.MyData.Dao;


import com.example.demo.MyData.Entity.Rm;

public interface RmDao {

     void saveRm(Rm rm);
     int getRmNum(int id);
     void reduceMoney(int rmId,int userName, int money);


}
