package org.example.client.service;

import org.example.client.controller.Resource1Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final Resource1Client resource1Client;

    public HelloService(Resource1Client resource1Client) {
        this.resource1Client = resource1Client;
    }

    @Scheduled(fixedRate = 60000) // 1 minute
    public void callExternalService() {
        System.out.println(resource1Client.callResource1());
    }
}
