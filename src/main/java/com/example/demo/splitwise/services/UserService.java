package com.example.demo.splitwise.services;

import com.example.demo.splitwise.models.User;
import com.example.demo.splitwise.repositories.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String userId, User user) {
        this.userRepository.getUserMap().put(userId, user);
    }

    public User getUser(String userId) {
        return this.userRepository.getUserMap().get(userId);
    }
}
