package org.example.client.service;

import lombok.RequiredArgsConstructor;
import org.example.client.controller.Resource1Client;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloService {

    private final Resource1Client resource1Client;

    @Scheduled(fixedRate = 10000)
    public void fixedRateTask() {
        System.out.println(resource1Client.callResource1());
    }

}
