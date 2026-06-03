package com.rodrigoguzman.spring_security.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloWorldController {
    @GetMapping("/secured-hello")
    public String securedHelloWorld() {
        return "Hello World safely";
    }
    
    @GetMapping("/non-secured-hello")
    public String nonSecuredHelloWorld() {
        return "Hello World, not secured";
    }
}
