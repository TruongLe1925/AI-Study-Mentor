package com._anhtai.aistudymentor.config;

import com._anhtai.aistudymentor.filter.JwtAuthFilter;
import com._anhtai.aistudymentor.repositoy.UserDetailsRepository;
import com._anhtai.aistudymentor.service.CustomUserDetailsService;
import com._anhtai.aistudymentor.utils.JWTUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Value("${SECRET_KEY}")
    private String secretKey;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userDetailsRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/subject/**").permitAll()
                        .requestMatchers("/api/chat/**").permitAll()
                        .anyRequest().permitAll()
                );
        http.addFilterBefore(new JwtAuthFilter(jwtUtils, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService)  {
        DaoAuthenticationProvider authenticationManager = new DaoAuthenticationProvider(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationManager)   ;
    }
}
