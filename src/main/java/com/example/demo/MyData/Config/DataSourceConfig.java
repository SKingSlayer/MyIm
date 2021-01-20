package com.example.demo.MyData.Config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.example.demo.DaoFactory.DaoFactory;
import com.example.demo.zookeeper.ZKCustor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan({"com.example.demo.MyData.Config.DataSourceTest","com.example.demo.MyData.Dao","com.example.demo.zookeeper"})
@Slf4j
public class DataSourceConfig {
    @Bean(initMethod = "init")
    public ZKCustor zkCustor(){
        return new ZKCustor();
    }
    @Bean(name = "DaoFactory")
    DaoFactory getDaoFactory() throws IOException {
        DaoFactory daoFactory=new DaoFactory();
        return  daoFactory;
    }

    //数据库db1数据源
    @Bean(name = "dataSource1")
    @ConfigurationProperties("spring.datasource.db1")
    @Primary
    public DruidDataSource dataSource1 () {
        return DruidDataSourceBuilder.create().build();
    }

    //数据库db2数据源
    @Bean(name = "dataSource2")
    @ConfigurationProperties("spring.datasource.db2")
    public DruidDataSource dataSource2 () {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dataSource3")
    @ConfigurationProperties("spring.datasource.db3")
    public DruidDataSource dataSource3 () {
        return DruidDataSourceBuilder.create().build();
    }

    //将两个数据源添加至动态数据源配置类中 //用autoweired
    @Bean(name = "myRoutingDataSource")
    public MyRoutingDataSource myRoutingDataSource (@Qualifier("dataSource1") DruidDataSource dataSource1,
                                                    @Qualifier("dataSource2") DruidDataSource dataSource2,
                                                    @Qualifier("dataSource3") DruidDataSource dataSource3) {
        Map<Object, Object> map = new HashMap<>();
        map.put(DataSourceType.DB1, dataSource1);
        map.put(DataSourceType.DB2, dataSource2);
        map.put(DataSourceType.DB3,dataSource3);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setTargetDataSources(map);
        myRoutingDataSource.setDefaultTargetDataSource(dataSource1);
        return myRoutingDataSource;
    }
    //数据源session
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory (@Qualifier("dataSource1") DruidDataSource dataSource1,
                                                @Qualifier("dataSource2") DruidDataSource dataSource2,
                                                @Qualifier("dataSource3") DruidDataSource dataSource3) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(myRoutingDataSource(dataSource1,dataSource2,dataSource3));

        return factoryBean.getObject();
    }

    //数据源事物配置
    @Bean
    public PlatformTransactionManager transactionManager (@Qualifier("myRoutingDataSource")MyRoutingDataSource myRoutingDataSource){
        return new DataSourceTransactionManager(myRoutingDataSource);
    }
}
