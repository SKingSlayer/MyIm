package com.example.demo.MyTest.DataSourceTest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {
    @Before("@annotation(ds)")
    public void beforeDataSource(MyDataSource ds) {
        DataSourceType value = ds.value();
        DataSourceContextHolder.setDataSource(value);
    }
    @After("@annotation(ds)")
    public void afterDataSource(MyDataSource ds){
        DataSourceContextHolder.clearDataSource();
    }
}