package org.example.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final Resource1Client resource1Client;

    @GetMapping("/")
    public String home() {
        return "Hello!!!";
    }

    @GetMapping("/callrs1")
    public String callRS1(){
        return resource1Client.callResource1();
    }
}
