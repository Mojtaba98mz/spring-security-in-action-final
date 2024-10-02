package com.example.resourceserver1.restController;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DemoController {
    @GetMapping("/demo")
    public String demo() {
        return "Demo";
    }

    @GetMapping("/demo1")
    public Authentication demo1(Authentication a) {
        return a;
    }
}