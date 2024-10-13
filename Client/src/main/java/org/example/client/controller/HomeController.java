package org.example.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {


    private final OAuth2AuthorizedClientService authorizedClientService;
    private final Resource1Client resource1Client;

    @GetMapping("/")
    public String home(/*Authentication authentication*/) {
//        getToken(authentication);
        return "Hello!!!";
    }

    /*private String getToken(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // Get the registered OAuth2 client
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                    .loadAuthorizedClient(
                            oauthToken.getAuthorizedClientRegistrationId(),
                            oauthToken.getName());

            // Retrieve the access token
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            return accessToken.getTokenValue();
        }
        return null;
    }*/

    @GetMapping("/callrs1")
    public String callRS1(){
        return resource1Client.callResource1();
    }
}
