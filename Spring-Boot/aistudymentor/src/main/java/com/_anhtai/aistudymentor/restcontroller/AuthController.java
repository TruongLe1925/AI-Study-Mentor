package com._anhtai.aistudymentor.restcontroller;

import com._anhtai.aistudymentor.dto.reponse.Token;
import com._anhtai.aistudymentor.dto.reponse.UserDTO;
import com._anhtai.aistudymentor.dto.request.AuthRequest;
import com._anhtai.aistudymentor.service.CustomUserDetailsService;
import com._anhtai.aistudymentor.service.UserService;
import com._anhtai.aistudymentor.utils.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    public AuthController(UserService userService,AuthenticationManager authenticationManager,JWTUtils jwtUtils,CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }
    @PostMapping("/auth/login")
        public ResponseEntity<Token> generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            return ResponseEntity.ok().body(jwtUtils.generateToken(authRequest.getEmail()));
        } catch (Exception e) {
            throw e;
        }
    }
    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO user) {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

}
