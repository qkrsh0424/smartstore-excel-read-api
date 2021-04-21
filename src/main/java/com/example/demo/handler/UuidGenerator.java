package com.example.demo.handler;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidGenerator {
    public String generator(){
        String[] uuid = UUID.randomUUID().toString().split("-");
        return uuid[2] +uuid[1] + uuid[0] + uuid[3] + uuid[4];
    }
}
