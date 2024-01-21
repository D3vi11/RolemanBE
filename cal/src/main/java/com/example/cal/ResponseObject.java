package com.example.cal;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseObject {
    private String message;
    @NonNull
    private HttpStatus status;
}
