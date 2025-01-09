package com.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync  // 비동기 처리 활성화
@EnableScheduling 
public class Football1Application {
    public static void main(String[] args) {
        SpringApplication.run(Football1Application.class, args);
    }
}
