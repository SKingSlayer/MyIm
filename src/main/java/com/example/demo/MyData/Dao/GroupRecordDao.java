package com.example.demo.MyData.Dao;

import com.example.demo.MyData.Entity.GroupRecord;
import com.example.demo.MyTest.DataSourceTest.DataSourceType;
import com.example.demo.MyTest.DataSourceTest.MyDataSource;
import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository("GroupRecordDao")
public interface GroupRecordDao {
  @MyDataSource(value = DataSourceType.DB3)
   @Insert("INSERT INTO group_record (user_id,group_id,record,time_stamp) values(#{userId},#{groupId},#{record},#{timeStamp})")
   void addGroupRecord(GroupRecord groupRecord);
   @Results(id = "GRMap", value = {
           @Result(id=true, column = "id", property = "id"),
           @Result(id=true, column = "user_id", property = "userId"),
           @Result(id=true, column = "group_id", property = "groupId"),
           @Result(column = "record", property = "record"),
           @Result(column = "time_stamp", property = "timeStamp")
   })

   @MyDataSource(value = DataSourceType.DB3)
   @Select("select * from group_record where group_id=#{groupId} and time_stamp>#{timeStamp}")
   List<GroupRecord> getGroupRecord(int groupId, Date timeStamp);
}
