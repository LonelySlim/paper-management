package com.pm.papermanagement.contoller;

import com.pm.papermanagement.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService service;

    @RequestMapping(value = "/hello")
    public String hello() {
        String username = "a";
        return service.hello(username);
    }


}
