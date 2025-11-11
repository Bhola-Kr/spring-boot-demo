package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        if (repo.existsByEmail(user.getEmail())) {
            return Map.of("error", "User already exists!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return Map.of("message", "Signup successful!");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        var existing = repo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(user.getPassword(), existing.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(existing.getEmail());
        return Map.of("token", token, "email", existing.getEmail());
    }

    @GetMapping("/me")
    public Map<String, Object> profile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        var user = repo.findByEmail(email).orElseThrow();
        return Map.of("id", user.getId(), "email", user.getEmail(), "name", user.getName());
    }
}
