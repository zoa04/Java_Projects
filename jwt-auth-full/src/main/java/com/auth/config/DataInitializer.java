package com.auth.config;

import com.auth.model.Role;
import com.auth.model.User;
import com.auth.repository.RoleRepository;
import com.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (roleRepo.findByName("ROLE_ADMIN").isEmpty()) roleRepo.save(new Role("ROLE_ADMIN"));
            if (roleRepo.findByName("ROLE_USER").isEmpty()) roleRepo.save(new Role("ROLE_USER"));
            if (!userRepo.existsByUsername("admin")) {
                User admin = new User("admin", encoder.encode("adminpass"));
                admin.getRoles().add(roleRepo.findByName("ROLE_ADMIN").get());
                admin.getRoles().add(roleRepo.findByName("ROLE_USER").get());
                userRepo.save(admin);
            }
        };
    }
}
