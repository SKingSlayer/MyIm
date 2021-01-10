package com.example.demo.MyTest.DataSourceTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.example.demo.MyTest.DataSourceTest")
public class DataSourceConfig {
    //数据库db1数据源
    @Bean(name = "dataSource1")
    @Primary
    @ConfigurationProperties("spring.datasource.db1")
    public DruidDataSource dataSource1 () {
        return DruidDataSourceBuilder.create().build();
    }

    //数据库db2数据源
    @Bean(name = "dataSource2")
    @ConfigurationProperties("spring.datasource.db2")
    public DruidDataSource dataSource2 () {
        return DruidDataSourceBuilder.create().build();
    }

    //将两个数据源添加至动态数据源配置类中
    @Bean(name = "myRoutingDataSource")
    public MyRoutingDataSource myRoutingDataSource (@Qualifier("dataSource1") DruidDataSource dataSource1,
                                                    @Qualifier("dataSource2") DruidDataSource dataSource2) {
        Map<Object, Object> map = new HashMap<>();
        map.put(DataSourceType.DB1, dataSource1);
        map.put(DataSourceType.DB2, dataSource2);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setTargetDataSources(map);
        myRoutingDataSource.setDefaultTargetDataSource(dataSource1);
        return myRoutingDataSource;
    }
    //数据源session
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory (@Qualifier("dataSource1") DruidDataSource dataSource1,
                                                @Qualifier("dataSource2") DruidDataSource dataSource2) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(myRoutingDataSource(dataSource1,dataSource2));
        // 设置mapper.xml的位置路径
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*/*.xml");
        factoryBean.setMapperLocations(resources);
        return factoryBean.getObject();
    }

    //数据源事物配置
    @Bean
    public PlatformTransactionManager transactionManager (@Qualifier("myRoutingDataSource")MyRoutingDataSource myRoutingDataSource){
        return new DataSourceTransactionManager(myRoutingDataSource);
    }
}