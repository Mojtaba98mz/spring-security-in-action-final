package org.example.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;

@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private final OAuth2AuthorizedClientManager authorizedClientManager;



    @Autowired
    public OAuth2FeignRequestInterceptor(OAuth2AuthorizedClientManager authorizedClientManager,
                                         ClientRegistrationRepository clientRegistrationRepository) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public void apply(RequestTemplate template) {

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("client-credentials")
                .principal("client")
                .build();

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        template.header("Authorization","Bearer "+accessToken);
        System.out.println(accessToken);
    }
}
