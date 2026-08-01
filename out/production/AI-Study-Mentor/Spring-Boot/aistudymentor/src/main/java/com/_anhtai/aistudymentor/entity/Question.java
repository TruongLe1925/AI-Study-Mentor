package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_Questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuestionID")
    private Integer questionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false, foreignKey = @ForeignKey(name = "FK_Questions_Users"))
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID", nullable = false, foreignKey = @ForeignKey(name = "FK_Questions_Subjects"))
    private Subject subject;
    
    @Column(name = "QuestionText", nullable = false, columnDefinition = "LONGTEXT")
    private String questionText;
    
    @Column(name = "ImageURL", length = 500)
    private String imageUrl;
    
    @Column(name = "DifficultyLevel", length = 20)
    private String difficultyLevel;
    
    @Column(name = "IsBookmarked")
    private Boolean isBookmarked = false;
    
    @Column(name = "AskedAt", updatable = false)
    private LocalDateTime askedAt = LocalDateTime.now();
}
