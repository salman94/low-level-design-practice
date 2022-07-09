package com.example.demo.splitwise.repositories;

import com.example.demo.splitwise.models.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserRepository {
    private Map<String, User> userMap;

    public UserRepository() {
        this.userMap = new HashMap<>();
    }
}
