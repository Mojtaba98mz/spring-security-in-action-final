package org.example.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class FeignConfig {

    @Bean
    public OAuth2FeignRequestInterceptor oauth2FeignRequestInterceptor(OAuth2AuthorizedClientManager authorizedClientManager,
                                                                       ClientRegistrationRepository clientRegistrationRepository) {
        return new OAuth2FeignRequestInterceptor(authorizedClientManager, clientRegistrationRepository);
    }
}

