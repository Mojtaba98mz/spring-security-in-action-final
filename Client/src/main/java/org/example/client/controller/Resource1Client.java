package org.example.client.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "resourceServer1", url = "http://localhost:9091")
public interface Resource1Client {

    @GetMapping("/demo")
    String callResource1(@RequestHeader("Authorization") String bearerToken);
}
