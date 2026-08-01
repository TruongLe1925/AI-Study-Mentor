package com.bonanhtai.aistudymentor.model;

import java.util.List;

public class QuizDTO {
    private List<QuestionDTO> questions;

    public QuizDTO() {}

    public QuizDTO(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
