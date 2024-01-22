package com.example.gen;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    private String message;
    private final HttpStatus status;
}
