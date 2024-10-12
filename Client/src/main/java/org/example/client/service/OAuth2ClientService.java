package org.example.client.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.stereotype.Service;

@Service
public class OAuth2ClientService {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public OAuth2ClientService(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    public String getAccessToken() {
        // Build the OAuth2AuthorizeRequest without needing the servlet request
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("client-app")
                .principal("client") // Machine-to-machine call, so use "client"
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
            return authorizedClient.getAccessToken().getTokenValue();
        }

        throw new IllegalStateException("Unable to retrieve access token");
    }
}
