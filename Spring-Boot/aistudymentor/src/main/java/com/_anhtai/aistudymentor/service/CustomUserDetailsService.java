package com._anhtai.aistudymentor.service;

import com._anhtai.aistudymentor.repositoy.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserDetailsRepository userDetailsRepository;
    public CustomUserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
