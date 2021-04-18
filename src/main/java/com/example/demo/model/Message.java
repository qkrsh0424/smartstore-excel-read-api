package com.example.demo.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Message {
    private HttpStatus status;
    private String message;
    private Object data;
}
