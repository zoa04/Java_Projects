package com.auth.controller;

import com.auth.model.Role;
import com.auth.model.User;
import com.auth.repository.RoleRepository;
import com.auth.repository.UserRepository;
import com.auth.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,Object> body) {
        String username = (String) body.get("username"); 
        String password = (String) body.get("password");
        Object rolesObj = body.get("roles"); // optional list of strings
        if (username==null || password==null) return ResponseEntity.badRequest().body("username/password required");
        if (userRepository.existsByUsername(username)) return ResponseEntity.badRequest().body("username already exists");

        User u = new User(username, passwordEncoder.encode(password));
        if (rolesObj instanceof java.util.List) {
            for (Object r : (java.util.List)rolesObj) {
                String rn = r.toString();
                Role role = roleRepository.findByName(rn).orElseGet(() -> roleRepository.save(new Role(rn)));
                u.getRoles().add(role);
            }
        } else {
            Role defaultRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
            u.getRoles().add(defaultRole);
        }
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String username = body.get("username"); String password = body.get("password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtUtils.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
