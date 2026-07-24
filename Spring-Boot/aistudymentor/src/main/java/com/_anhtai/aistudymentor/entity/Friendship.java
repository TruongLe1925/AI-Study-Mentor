package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_Friendships", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"UserID", "FriendID"}, name = "UQ_User_Friend"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FriendshipID")
    private Integer friendshipId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false, foreignKey = @ForeignKey(name = "FK_Friendships_User"))
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FriendID", nullable = false, foreignKey = @ForeignKey(name = "FK_Friendships_Friend"))
    private User friend;
    
    @Column(name = "Status", length = 20)
    private String status = "Pending";
    
    @Column(name = "CreatedAt", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
