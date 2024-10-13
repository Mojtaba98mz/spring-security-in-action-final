package org.example.client.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableFeignClients
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.oauth2Login(Customizer.withDefaults());
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated());
        return http.build();
    }

    /*@Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                                 OAuth2AuthorizedClientService authorizedClientService) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        return new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientService);
    }*/

    /*@Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration c1 =
                ClientRegistration.withRegistrationId("1")
                        .clientId("client")
                        .clientSecret("secret")
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .clientAuthenticationMethod(
                                ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .tokenUri("http://localhost:7070/oauth2/token")
                        .scope(OidcScopes.OPENID)
                        .build();
        var repository =
                new InMemoryClientRegistrationRepository(c1);
        return repository;
    }*/

}
