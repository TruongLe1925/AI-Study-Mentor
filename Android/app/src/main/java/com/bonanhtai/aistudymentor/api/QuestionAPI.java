package com.bonanhtai.aistudymentor.api;

import com.bonanhtai.aistudymentor.model.AnswerDTO;
import com.bonanhtai.aistudymentor.model.AskDTO;
import com.bonanhtai.aistudymentor.model.QuestionDTO;
import com.bonanhtai.aistudymentor.model.QuestionHistory;
import com.bonanhtai.aistudymentor.model.QuizDTO;
import com.bonanhtai.aistudymentor.model.QuizRequestDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface QuestionAPI {
    @Multipart
    @POST("api/question/ask")
    Call<AnswerDTO> askQuestion(
            @Part("question") RequestBody question,
            @Part MultipartBody.Part file
    );

    @POST("api/question/quiz")
    Call<QuizDTO> QuizGen(@Body QuizRequestDTO quizRequestDTO);

    @GET("api/question/questions")
    Call<List<QuestionHistory>> getQuestions();
}
