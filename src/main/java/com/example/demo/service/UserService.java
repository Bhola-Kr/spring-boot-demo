package com.example.demo.service;

import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor-based dependency injection (recommended)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID (throws exception if not found)
    public Optional<User> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("❌ User not found with ID: " + id);
        }
        return user;
    }

    // Get user by ID (throws exception if not found)
    @Cacheable(value = "users", key = "#id")
    public User addUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmailException("⚠️ Email already exists: " + user.getEmail());
        } catch (Exception ex) {
            throw new RuntimeException("🚨 Failed to add user: " + ex.getMessage(), ex);
        }
    }

    // Update existing user
    public Optional<User> updateUser(int id, User updatedUser) {
        return userRepository.findById(id).map(existing -> {
            existing.setName(updatedUser.getName());
            existing.setEmail(updatedUser.getEmail());
            try {
                return userRepository.save(existing);
            } catch (DataIntegrityViolationException ex) {
                throw new DuplicateEmailException("⚠️ Email already exists: " + updatedUser.getEmail());
            } catch (Exception ex) {
                throw new RuntimeException("🚨 Failed to update user: " + ex.getMessage(), ex);
            }
        }).or(() -> {
            throw new UserNotFoundException("❌ Cannot update. User not found with ID: " + id);
        });
    }

    // Delete user by ID
    @CacheEvict(value = "users", key = "#id")
    public boolean deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("❌ Cannot delete. User not found with ID: " + id);
        }
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("🚨 Failed to delete user: " + ex.getMessage(), ex);
        }
    }
}
