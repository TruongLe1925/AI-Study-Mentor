package com.bonanhtai.aistudymentor.model;

public class QuizRequestDTO {
    private String subject;
    private Token token;

    public QuizRequestDTO(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
