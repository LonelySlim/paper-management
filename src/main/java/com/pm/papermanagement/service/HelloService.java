package com.pm.papermanagement.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String hello(String username) {
        return "hello, 111" +username;
    }
}
