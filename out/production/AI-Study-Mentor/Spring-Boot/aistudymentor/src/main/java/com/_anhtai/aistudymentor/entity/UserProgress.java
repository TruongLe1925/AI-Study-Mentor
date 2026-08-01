package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_UserProgress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgressID")
    private Integer progressId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_UserProgress_Users"))
    private User user;
    
    @Column(name = "TotalQuestionsAsked")
    private Integer totalQuestionsAsked = 0;
    
    @Column(name = "QuizAccuracyRate")
    private Float quizAccuracyRate = 0.0f;
    
    @Column(name = "TimeSpentReviewing")
    private Integer timeSpentReviewing = 0;
}
