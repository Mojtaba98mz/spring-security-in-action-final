package com.example.authorizationserver.config;

import com.example.authorizationserver.model.Role;
import com.example.authorizationserver.model.Users;
import com.example.authorizationserver.repository.RoleRepository;
import com.example.authorizationserver.repository.UsersRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DatabaseInitializerConfig {

    @Bean
    ApplicationRunner init(UsersRepository usersRepository, RoleRepository roleRepository) {
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
        };
    }
}

