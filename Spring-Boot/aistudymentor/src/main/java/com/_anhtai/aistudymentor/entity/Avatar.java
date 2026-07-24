package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_Avatars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AvatarID")
    private Integer avatarId;
    
    @Column(name = "AvatarName", nullable = false, length = 100)
    private String avatarName;
    
    @Column(name = "ImageURL", nullable = false, length = 500)
    private String imageUrl;
    
    @Column(name = "RequiredLevel")
    private Integer requiredLevel = 1;
}
