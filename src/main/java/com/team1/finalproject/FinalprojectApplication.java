package com.team1.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinalprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalprojectApplication.class, args);
    }

}
