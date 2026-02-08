package com.cinemax.service;

import com.cinemax.model.User;
import com.cinemax.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String rawPassword) {
        try {
            Optional<User> existing = userRepository.findByEmail(email);
            if (existing.isEmpty()) return Optional.empty();

            User user = existing.get();
            if (user.getPassword() == null) return Optional.empty();

            if (encoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
