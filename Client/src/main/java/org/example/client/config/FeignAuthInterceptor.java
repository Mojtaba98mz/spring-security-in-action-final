package org.example.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.example.client.service.OAuth2ClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final OAuth2ClientService oAuth2ClientService;

    @Value("${spring.security.oauth2.client.registration.client-app.client-id}")
    private String clientId;
    @Value("${spring.application.name}")
    private String appName;


    public FeignAuthInterceptor(OAuth2AuthorizedClientService authorizedClientService, OAuth2ClientService oAuth2ClientService) {
        this.authorizedClientService = authorizedClientService;
        this.oAuth2ClientService = oAuth2ClientService;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Check if Authorization header is already present
        var existToken = getToken();
        if (existToken == null) {
            String accessToken = oAuth2ClientService.getAccessToken();
            var authorizedClient = authorizedClientService.loadAuthorizedClient(appName,clientId);
            if (authorizedClient != null) {
                var tokenValue = authorizedClient.getAccessToken().getTokenValue();
                requestTemplate.header("Authorization", "Bearer " + tokenValue);
            }
        }else {
            requestTemplate.header("Authorization", "Bearer " + existToken);
        }
    }

    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
                // Get the registered OAuth2 client
                OAuth2AuthorizedClient authorizedClient = authorizedClientService
                        .loadAuthorizedClient(
                                oauthToken.getAuthorizedClientRegistrationId(),
                                oauthToken.getName());
                // Retrieve the access token
                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
                return accessToken.getTokenValue();
            }
        }
        return null;
    }
}

