package com._anhtai.aistudymentor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_AIAnswers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIAnswer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnswerID")
    private Integer answerId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuestionID", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_AIAnswers_Questions"))
    private Question question;
    
    @Column(name = "PrimaryAnswer", nullable = false, columnDefinition = "LONGTEXT")
    private String primaryAnswer;
    
    @Column(name = "SimplifiedExplanation", columnDefinition = "LONGTEXT")
    private String simplifiedExplanation;
    
    @Column(name = "AlternativeApproaches", columnDefinition = "LONGTEXT")
    private String alternativeApproaches;
    
    @Column(name = "KeyConceptsSummary", columnDefinition = "LONGTEXT")
    private String keyConceptsSummary;
    
    @Column(name = "CommonMistakes", columnDefinition = "LONGTEXT")
    private String commonMistakes;
    
    @Column(name = "SuggestedFollowUps", columnDefinition = "LONGTEXT")
    private String suggestedFollowUps;
    
    @Column(name = "GeneratedAt", updatable = false)
    private LocalDateTime generatedAt = LocalDateTime.now();
}
