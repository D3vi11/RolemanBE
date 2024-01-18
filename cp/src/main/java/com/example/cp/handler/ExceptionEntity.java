package com.example.cp.handler;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ExceptionEntity {
    HttpStatus status;
    String message;
}
