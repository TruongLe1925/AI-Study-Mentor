package com.bonanhtai.aistudymentor.model;

import java.time.LocalDateTime;

public class User {

    private String email;

    private String passwordHash;

    private String educationLevel;

    private String role;

    private String preferredStyle;

    private Integer totalXp = 0;

    private Integer currentLevel = 1;

    private Avatar selectedAvatar;

    private Theme selectedTheme;

}
