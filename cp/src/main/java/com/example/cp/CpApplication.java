package com.example.cp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CpApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpApplication.class, args);
    }

}
