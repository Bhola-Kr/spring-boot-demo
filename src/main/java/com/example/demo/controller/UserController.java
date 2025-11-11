package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // ✅ GET all users with pagination and filters
    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id
    ) {
        var pageable = PageRequest.of(page, size);

        // If ID is provided → fetch that specific user
        if (id != null) {
            return repo.findById(id)
                    .map(user -> new org.springframework.data.domain.PageImpl<>(java.util.List.of(user)))
                    .orElseGet(() -> new org.springframework.data.domain.PageImpl<>(java.util.Collections.emptyList(), pageable, 0));

        }

        // If name filter provided → filter users by name
        if (name != null && !name.isEmpty()) {
            var users = repo.findByNameFilter(name);
            return new org.springframework.data.domain.PageImpl<>(users, pageable, users.size());
        }

        // Otherwise return all users paginated
        return repo.findAll(pageable);
    }
}
