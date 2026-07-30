package com._anhtai.aistudymentor.restcontroller;

import com._anhtai.aistudymentor.dto.request.AuthRequest;
import com._anhtai.aistudymentor.utils.JWTUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    public AuthController(AuthenticationManager authenticationManager,JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/api/auth/register")
        public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            return ResponseEntity.ok().body(jwtUtils.generateToken(authRequest.getEmail()));
        } catch (Exception e) {
            throw e;
        }
    }
}
