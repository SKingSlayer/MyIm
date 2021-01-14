package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.ChatRecord;
import com.example.demo.MyTest.DataSourceTest.DataSourceType;
import com.example.demo.MyTest.DataSourceTest.MyDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Mapper
@Repository
public interface ChatRecordDao {
    List<ChatRecord> getRecord(int userId,int friendId,Date date);
    void addRecord(ChatRecord chatRecord);
    @Results(id = "userMap", value = {
            @Result(id=true, column = "user_id", property = "userId"),
            @Result(column = "friend_id", property = "friendId"),
            @Result(column = "record", property = "record"),
            @Result(column = "time_stamp", property = "timeStamp")
    })
    @MyDataSource(value = DataSourceType.DB1)
    @Select("select * from chat_record where match(record) against(CONCAT('%',#{s},'%'))")
    List<ChatRecord> getRecordByFullIndex(String s);

}
