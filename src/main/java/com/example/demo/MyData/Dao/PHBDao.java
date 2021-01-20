package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.PHB;
import com.example.demo.MyData.Config.DataSourceType;
import com.example.demo.MyData.Config.MyDataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository("PHBDao")
@Mapper
public interface PHBDao {
    @Results(id ="phbMap",value={
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_id",property = "userId"),
            @Result(column = "friend_id",property = "friendId"),
            @Result(column = "money",property = "money"),
            @Result(column = "time_stamp",property = "timeStamp")
    })
    @Select("select * from phb where id=#{id}")
    PHB getPHB(int id);

    @Select("select max(id) from phb ")
    int getLastId();
    @MyDataSource(DataSourceType.DB1)
    @Insert("insert into phb (user_id,friend_id,money,time_stamp) values(#{userId},#{friendId},#{money},#{timeStamp})")
    void addPHB(PHB phb);


    @Select("select money from phb where id=#{id}")
    int getMoney(int id);
    @MyDataSource(DataSourceType.DB1)
    @Update("update phb set money=money-#{num} where  id=#{id} ") //既然会重优化，顺序不重要，做实验
    void subMoney1(int id,Double currentMoney,Double num);

    @MyDataSource(DataSourceType.DB2)
    @Update("update phb set money=money-#{num} where money=#{currentMoney} and id=#{id} ") //既然会重优化，顺序不重要，做实验
    void subMoney2(int id,Double currentMoney,Double num);

    @Update("update phb set money=#{money} where  id=#{id} ")
    void setMoney(int id,int money);

    @Delete("delete from phb where id=#{id} ")
    void deletePHB(int id);

}
