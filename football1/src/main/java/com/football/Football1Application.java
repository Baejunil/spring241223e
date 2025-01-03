package com.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 스케줄링 기능 활성화
public class Football1Application {
    public static void main(String[] args) {
        SpringApplication.run(Football1Application.class, args);
    }
}
