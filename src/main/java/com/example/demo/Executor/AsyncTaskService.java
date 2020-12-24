package com.example.demo.Executor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
public class AsyncTaskService {
    //表明该方法是异步方法，如果注解在类上，则表明该类的所有方法都是异步方法，
    //这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
    @Async
    public void executeAsyncTask(Integer i){
        System.out.println("执行异步任务: "+i);
    }

    @Async
    public void executeAsyncTaskPlus(Integer i){
        System.out.println("执行异步任务+1: "+(i+1));
    }

}
