package com.example.authorizationserver.config;

import com.example.authorizationserver.model.Role;
import com.example.authorizationserver.model.Users;
import com.example.authorizationserver.repository.RegisteredClientJpaRepository;
import com.example.authorizationserver.repository.RoleRepository;
import com.example.authorizationserver.repository.UsersRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Set;
import java.util.UUID;

@Configuration
public class DatabaseInitializerConfig {

    @Bean
    ApplicationRunner init(UsersRepository usersRepository,
                           RoleRepository roleRepository,
                           JpaRegisteredClientRepository clientJpaRepository) {
        return args -> {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);

            // Inserting users
            Users admin = new Users();
            admin.setUsername("mojtaba");
            admin.setPassword("123");
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            admin.setEnabled(true);
            admin.setRoles(Set.of(adminRole,userRole));
            usersRepository.save(admin);

            Users user = new Users();
            user.setUsername("rosa");
            user.setPassword("123");
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            user.setRoles(Set.of(userRole));
            usersRepository.save(user);

            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("client")
                    .clientSecret("secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri("https://example.com/authorized")
                    .scope(OidcScopes.OPENID)
                    .build();
            clientJpaRepository.save(registeredClient);
        };
    }
}

