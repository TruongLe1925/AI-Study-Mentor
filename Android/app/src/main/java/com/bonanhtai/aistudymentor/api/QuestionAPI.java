package com.bonanhtai.aistudymentor.api;

import com.bonanhtai.aistudymentor.model.AnswerDTO;
import com.bonanhtai.aistudymentor.model.AskDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface QuestionAPI {
    @POST("api/question/ask")
    Call<AnswerDTO> askQuestion(@Body AskDTO question);
}
