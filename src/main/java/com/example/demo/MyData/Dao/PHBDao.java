package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.PHB;

import java.util.Date;

public interface PHBDao {
    PHB getPHB(int id);
    void addPHB(PHB phb);
    int getLastId();
    void deletePHB(int id);

}
