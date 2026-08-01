package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_GamificationBadges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamificationBadge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BadgeID")
    private Integer badgeId;
    
    @Column(name = "BadgeName", nullable = false, length = 100)
    private String badgeName;
    
    @Column(name = "Description", length = 255)
    private String description;
    
    @Column(name = "RewardType", length = 50)
    private String rewardType;
}
