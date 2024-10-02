package com.example.resourceserver1.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Getter
public class CustomAuthentication extends JwtAuthenticationToken {
    private final String priority;
    public CustomAuthentication(
            Jwt jwt,
            Collection<? extends GrantedAuthority> authorities,
            String priority) {
        super(jwt, authorities);
        this.priority = priority;
    }
}