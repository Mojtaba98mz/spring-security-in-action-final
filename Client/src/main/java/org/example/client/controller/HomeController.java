package org.example.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // Get the registered OAuth2 client
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                    .loadAuthorizedClient(
                            oauthToken.getAuthorizedClientRegistrationId(),
                            oauthToken.getName());

            // Retrieve the access token
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            System.out.println("Token Value: " + accessToken.getTokenValue());
        }
        return "index.html";
    }
}