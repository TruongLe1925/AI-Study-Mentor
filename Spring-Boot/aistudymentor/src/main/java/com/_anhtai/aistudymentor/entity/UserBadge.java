package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_UserBadges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBadge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserBadgeID")
    private Integer userBadgeId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false, foreignKey = @ForeignKey(name = "FK_UserBadges_Users"))
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BadgeID", nullable = false, foreignKey = @ForeignKey(name = "FK_UserBadges_Badges"))
    private GamificationBadge badge;
    
    @Column(name = "AwardedAt", updatable = false)
    private LocalDateTime awardedAt = LocalDateTime.now();
}
