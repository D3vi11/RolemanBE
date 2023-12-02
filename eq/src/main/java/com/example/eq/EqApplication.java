package com.example.eq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EqApplication {

    public static void main(String[] args) {
        SpringApplication.run(EqApplication.class, args);
    }

}
