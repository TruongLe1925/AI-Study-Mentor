package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_users")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;
    
    @Column(name = "Email", nullable = false, unique = true, length = 150)
    private String email;
    
    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;
    
    @Column(name = "EducationLevel", length = 50)
    private String educationLevel;

    @Column(name = "Role", length = 20)
    private String role;

    @Column(name = "PreferredStyle", length = 50)
    private String preferredStyle;
    
    @Column(name = "TotalXP")
    private Integer totalXp = 0;
    
    @Column(name = "CurrentLevel")
    private Integer currentLevel = 1;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SelectedAvatarID", foreignKey = @ForeignKey(name = "FK_Users_Avatars"))
    private Avatar selectedAvatar;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SelectedThemeID", foreignKey = @ForeignKey(name = "FK_Users_Themes"))
    private Theme selectedTheme;

    @Column(name = "CreatedAt", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
