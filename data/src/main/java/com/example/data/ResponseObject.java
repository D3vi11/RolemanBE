package com.example.data;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseObject {
    private String message;
    private final HttpStatus status;
}
