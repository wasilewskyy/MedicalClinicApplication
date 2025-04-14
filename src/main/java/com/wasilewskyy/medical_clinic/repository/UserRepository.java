package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public void save(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }
        users.add(user);
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public void deleteByUsername(String username) {
        users.removeIf(u -> u.getUsername().equals(username));
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public void update(String username, User updatedUser) {
        User user = findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(updatedUser.getPassword());
    }
}
