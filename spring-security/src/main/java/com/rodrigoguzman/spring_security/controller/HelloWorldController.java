package com.rodrigoguzman.spring_security.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@PreAuthorize("denyAll()")
public class HelloWorldController {
    @GetMapping("/secured-hello")
    @PreAuthorize("hasRole('ADMIN')")
    public String securedHelloWorld() {
        return "Hello World safely";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/non-secured-hello")
    public String nonSecuredHelloWorld() {
        return "Hello World, not secured";
    }
}
