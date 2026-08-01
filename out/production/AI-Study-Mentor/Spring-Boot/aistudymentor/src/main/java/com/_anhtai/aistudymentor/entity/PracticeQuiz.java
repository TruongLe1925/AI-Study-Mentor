package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_PracticeQuizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeQuiz {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuizID")
    private Integer quizId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false, foreignKey = @ForeignKey(name = "FK_PracticeQuizzes_Users"))
    private User user;
    
    @Column(name = "QuestionType", length = 50)
    private String questionType;
    
    @Column(name = "QuizContent", nullable = false, columnDefinition = "LONGTEXT")
    private String quizContent;
    
    @Column(name = "CorrectAnswer", nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;
    
    @Column(name = "UserAnswer", columnDefinition = "TEXT")
    private String userAnswer;
    
    @Column(name = "IsCorrect")
    private Boolean isCorrect = false;
    
    @Column(name = "AttemptDate", updatable = false)
    private LocalDateTime attemptDate = LocalDateTime.now();
}
