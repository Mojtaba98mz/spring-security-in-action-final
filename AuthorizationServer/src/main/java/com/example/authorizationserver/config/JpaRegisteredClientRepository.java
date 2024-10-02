package com.example.authorizationserver.config;

import com.example.authorizationserver.model.RegisteredClientEntity;
import com.example.authorizationserver.repository.RegisteredClientJpaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.stream.Collectors;

@Configuration
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final RegisteredClientJpaRepository registeredClientJpaRepository;

    public JpaRegisteredClientRepository(RegisteredClientJpaRepository registeredClientJpaRepository) {
        this.registeredClientJpaRepository = registeredClientJpaRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        RegisteredClientEntity entity = toEntity(registeredClient);
        registeredClientJpaRepository.save(entity);
    }

    @Override
    public RegisteredClient findById(String id) {
        return registeredClientJpaRepository.findById(id)
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registeredClientJpaRepository.findByClientId(clientId)
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    private RegisteredClientEntity toEntity(RegisteredClient registeredClient) {
        RegisteredClientEntity entity = new RegisteredClientEntity();
        entity.setId(registeredClient.getId());
        entity.setClientId(registeredClient.getClientId());
        entity.setClientSecret(registeredClient.getClientSecret());
        entity.setClientAuthenticationMethods(
                registeredClient.getClientAuthenticationMethods().stream()
                        .map(method -> method.getValue()).collect(Collectors.toSet())
        );
        entity.setAuthorizationGrantTypes(
                registeredClient.getAuthorizationGrantTypes().stream()
                        .map(grantType -> grantType.getValue()).collect(Collectors.toSet())
        );
        entity.setRedirectUris(registeredClient.getRedirectUris());
        entity.setScopes(registeredClient.getScopes());
        return entity;
    }

    private RegisteredClient toRegisteredClient(RegisteredClientEntity entity) {
        return RegisteredClient
                .withId(entity.getId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .clientAuthenticationMethods(authMethods ->
                        entity.getClientAuthenticationMethods().forEach(
                                method -> authMethods.add(new ClientAuthenticationMethod(method))
                        )
                )
                .authorizationGrantTypes(grantTypes ->
                        entity.getAuthorizationGrantTypes().forEach(
                                grant -> grantTypes.add(new AuthorizationGrantType(grant))
                        )
                )
                .redirectUris(uris -> uris.addAll(entity.getRedirectUris()))
                .scopes(scopes -> scopes.addAll(entity.getScopes()))
                .build();
    }
}

