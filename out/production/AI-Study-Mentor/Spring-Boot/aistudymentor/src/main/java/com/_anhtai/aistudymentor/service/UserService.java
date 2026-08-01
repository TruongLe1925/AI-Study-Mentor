package com._anhtai.aistudymentor.service;

import com._anhtai.aistudymentor.dto.reponse.UserDTO;
import com._anhtai.aistudymentor.entity.User;
import com._anhtai.aistudymentor.exception.EmailIsPresentException;
import com._anhtai.aistudymentor.repositoy.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;
    public UserService(PasswordEncoder passwordEncoder,UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void save(UserDTO user) {
        if(userDetailsRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailIsPresentException("User already exists");
        }
        User newUser = User.builder()
                .email(user.getEmail())
                .educationLevel(user.getEducationLevel())
                .passwordHash(passwordEncoder.encode(user.getPassword()))
                .totalXp(0)
                .createdAt(LocalDateTime.now())
                .role("STUDENT")
                .build();
        userDetailsRepository.save(newUser);

    }
}
