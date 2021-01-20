package com.example.demo.MyConfig;

import com.example.demo.MyTest.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyTest {
    public void test() throws InterruptedException {
        while (true){
            Thread.sleep(1000);
            log.info("hello world");
        }

    }
}
