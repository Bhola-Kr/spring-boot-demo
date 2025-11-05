package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserJdbcRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserJdbcService {

    private final UserJdbcRepository userJdbcRepository;

    public UserJdbcService(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    public List<User> getAllUsers() {
        return userJdbcRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userJdbcRepository.findById(id);
    }

    public boolean addUser(User user) {
        return userJdbcRepository.save(user) > 0;
    }

    public boolean updateUser(int id, User user) {
        return userJdbcRepository.update(id, user) > 0;
    }

    public boolean deleteUser(int id) {
        return userJdbcRepository.delete(id) > 0;
    }
}
