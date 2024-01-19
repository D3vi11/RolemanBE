package com.example.cp;

import org.springframework.http.HttpStatus;

public record ResponseObject(String message, HttpStatus status) {
}
