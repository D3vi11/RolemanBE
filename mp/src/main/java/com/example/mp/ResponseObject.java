package com.example.mp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    String message;
    @NonNull
    HttpStatus status;
}
