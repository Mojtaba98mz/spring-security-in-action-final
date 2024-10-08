package com.example.authorizationserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.UUID;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

//    private final CorsConfigurationSource corsConfigurationSource;
    @Bean
    @Order(1)
    public SecurityFilterChain asFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration
                .applyDefaultSecurity(http);
        http.cors(AbstractHttpConfigurer::disable);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

//        http.cors(c->c.configurationSource(corsConfigurationSource));

        http.exceptionHandling((e) ->
                e.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());
        http.cors(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /*@Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient =
                RegisteredClient
                        .withId(UUID.randomUUID().toString())
                        .clientId("client")
                        .clientSecret("secret")
                        .clientAuthenticationMethod(
                                ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(
                                AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(
                                AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("https://www.manning.com/authorized")
//                        .tokenSettings(TokenSettings.builder()
//                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
//                                .build())
                        .scope(OidcScopes.OPENID)
                        .build();
        return new InMemoryRegisteredClientRepository(registeredClient);*/
//    }

    /*@Bean
    public JWKSource<SecurityContext> jwkSource()
            throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey =
                (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey =
                (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }*/

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            JwtClaimsSet.Builder claims = context.getClaims();
            claims.claim("priority", "HIGH");
        };
    }

}
