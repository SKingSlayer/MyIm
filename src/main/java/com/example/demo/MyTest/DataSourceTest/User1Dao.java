package com.example.demo.MyTest.DataSourceTest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface User1Dao {
    @MyDataSource(value = DataSourceType.DB1)
    @Select("select * from user where id=#{id}")
    UserEntity queryUserById (@Param("id") int id);

}