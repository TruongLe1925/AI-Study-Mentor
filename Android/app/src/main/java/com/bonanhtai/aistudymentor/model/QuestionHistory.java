package com.bonanhtai.aistudymentor.model;

import java.time.LocalDateTime;

public class QuestionHistory {
    private String questionText;
    private String subject;
    private String primaryAnswer;
    private String simplifiedExplanation;
    private LocalDateTime askedAt;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPrimaryAnswer() {
        return primaryAnswer;
    }

    public void setPrimaryAnswer(String primaryAnswer) {
        this.primaryAnswer = primaryAnswer;
    }

    public String getSimplifiedExplanation() {
        return simplifiedExplanation;
    }

    public void setSimplifiedExplanation(String simplifiedExplanation) {
        this.simplifiedExplanation = simplifiedExplanation;
    }

    public LocalDateTime getAskedAt() {
        return askedAt;
    }

    public void setAskedAt(LocalDateTime askedAt) {
        this.askedAt = askedAt;
    }
}
