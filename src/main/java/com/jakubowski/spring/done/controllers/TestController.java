package com.jakubowski.spring.done.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/error")
    public String error() {
        return "ERROR";
    }
}
