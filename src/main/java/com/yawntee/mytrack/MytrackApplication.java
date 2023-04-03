package com.yawntee.mytrack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yawntee.mytrack.mapper")
@SpringBootApplication()
public class MytrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytrackApplication.class, args);
    }

}
