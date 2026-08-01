package com._anhtai.aistudymentor.repositoy;

import com._anhtai.aistudymentor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
