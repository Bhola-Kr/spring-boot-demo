package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Initial dummy data
        users.add(new User(1, "Bhola Kumar", "bhola.kumar@sap.com"));
        users.add(new User(2, "Praveen Dubey", "praveen.dubey@sap.com"));
        users.add(new User(3, "Bhanu Singh", "bhanu.singh@sap.com"));
        users.add(new User(4, "Kuldeep Singh", "kuldeep.singh@sap.com"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public Optional<User> updateUser(int id, User updatedUser) {
        return getUserById(id).map(existing -> {
            existing.setName(updatedUser.getName());
            existing.setEmail(updatedUser.getEmail());
            return existing;
        });
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}
