package com.example.cal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalApplication.class, args);
    }

}
