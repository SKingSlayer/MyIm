package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.GHB;
import com.example.demo.MyData.Config.DataSourceType;
import com.example.demo.MyData.Config.MyDataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("GHBDao")
public interface GHBDao {
    @Results(id ="ghbMap",value={
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_id",property = "userId"),
            @Result(column = "group_id",property = "groupId"),
            @Result(column = "money",property = "money"),
            @Result(column = "size",property = "size"),
            @Result(column = "time_stamp",property = "timeStamp")
    })
    @Select("select * from ghb where id=#{id}")
    GHB getGHB(int id);

    @Select("select max(id) from ghb ")
    int getLastId();
    @MyDataSource(DataSourceType.DB1)
    @Insert("insert into ghb (user_id,group_id,money,size,time_stamp) values(#{userId},#{groupId},#{money},#{size},#{timeStamp})")
    void addGHB1(GHB ghb);
    @MyDataSource(DataSourceType.DB2)
    @Insert("insert into ghb (user_id,group_id,money,size,time_stamp) values(#{userId},#{groupId},#{money},#{size},#{timeStamp})")
    void addGHB2(GHB ghb);

    @Select("select money from ghb where id=#{id}")
    int getMoney(int id);
    @MyDataSource(DataSourceType.DB1)
    @Update("update ghb set money=money-#{num} where  id=#{id} ") //既然会重优化，顺序不重要，做实验
    void subMoney1(int id,Double currentMoney,Double num);

    @MyDataSource(DataSourceType.DB2)
    @Update("update ghb set money=money-#{num} where money=#{currentMoney} and id=#{id} ") //既然会重优化，顺序不重要，做实验
    void subMoney2(int id,Double currentMoney,Double num);

    @Update("update ghb set money=#{money} where  id=#{id} ")
    void setMoney(int id,int money);
    @Update("update ghb set size=size-1 where  id=#{id} ")
    void setSize(int id);
}
