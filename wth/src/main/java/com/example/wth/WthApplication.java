package com.example.wth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WthApplication.class, args);
    }

}
