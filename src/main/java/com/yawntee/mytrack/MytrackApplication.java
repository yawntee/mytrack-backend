package com.yawntee.mytrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MytrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytrackApplication.class, args);
    }

}
