package com.example.demo.MyData.Dao;
import com.example.demo.MyData.Entity.PersonalRecord;
import com.example.demo.MyData.Config.DataSourceType;
import com.example.demo.MyData.Config.MyDataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Mapper
@Repository("PersonalRecordDao")
public interface PersonalRecordDao {
    List<PersonalRecord> getRecord(int userId, int friendId, Date date);
    @Insert("insert into personal_record (user_id,friend_id,record,time_stamp) values(#{userId},#{friendId},#{record},#{timeStamp})")
    void addRecord(PersonalRecord personalRecord);
    @Results(id = "userMap", value = {
            @Result(id=true, column = "user_id", property = "userId"),
            @Result(column = "friend_id", property = "friendId"),
            @Result(column = "record", property = "record"),
            @Result(column = "time_stamp", property = "timeStamp")
    })
    @MyDataSource(value = DataSourceType.DB1)
    @Select("select * from personal_record where match(record) against(CONCAT('%',#{s},'%')) and ((user_id=#{userId} and friend_id=#{friendId}) or (user_id=#{friendId} and friend_id=#{userId}))")
    List<PersonalRecord> getRecordByFullIndex(int userId,int friendId,String s);//

}
