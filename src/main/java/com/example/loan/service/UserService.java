package com.example.loan.service;
import com.example.loan.entity.UserEntity;
import com.example.loan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create User
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    // Update User (same as create, just re-save)
    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    // Find User by ID
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    // Find User by Username
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
