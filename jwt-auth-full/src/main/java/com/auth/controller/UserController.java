package com.auth.controller;

import com.auth.model.User;
import com.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> listUsers() { return userRepository.findAll(); }

    @GetMapping("/profile")
    public Principal profile(Principal p) { return p; }
}
