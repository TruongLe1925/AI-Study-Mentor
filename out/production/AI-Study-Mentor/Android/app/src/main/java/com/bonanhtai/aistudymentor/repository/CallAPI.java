package com.bonanhtai.aistudymentor.repository;

import android.content.Context;
import android.util.Log;

import com.bonanhtai.aistudymentor.api.ApiCallback;
import com.bonanhtai.aistudymentor.api.AuthAPI;
import com.bonanhtai.aistudymentor.api.QuestionAPI;
import com.bonanhtai.aistudymentor.api.RegisterAPI;
import com.bonanhtai.aistudymentor.model.AnswerDTO;
import com.bonanhtai.aistudymentor.model.AskDTO;
import com.bonanhtai.aistudymentor.model.AuthRequest;
import com.bonanhtai.aistudymentor.model.QuizDTO;
import com.bonanhtai.aistudymentor.model.QuizRequestDTO;
import com.bonanhtai.aistudymentor.model.Token;
import com.bonanhtai.aistudymentor.model.UserDTO;
import com.bonanhtai.aistudymentor.retrofit.RetrofitService;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallAPI {
    private final RetrofitService retrofitService;
    private final Gson gson = new Gson();

    public CallAPI(Context context) {
        this.retrofitService = new RetrofitService(context);
    }

    public void askApi(AskDTO askDTO, File imageFile, ApiCallback<AnswerDTO> callback) {

        QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);

        // Convert AskDTO to JSON RequestBody
        String json = gson.toJson(askDTO);
        RequestBody questionPart = RequestBody.create(MediaType.parse("application/json"), json);

        // Create Image Part
        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);
        }

        questionAPI.askQuestion(questionPart, imagePart)
                .enqueue(new Callback<AnswerDTO>() {
                    @Override
                    public void onResponse(Call<AnswerDTO> call, Response<AnswerDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            AnswerDTO answerDTO = response.body();
                            callback.onSuccess(answerDTO);
                        } else {
                            Log.e("API_ERROR", "Code: " + response.code());
                            try {
                                if (response.errorBody() != null) {
                                    Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AnswerDTO> call, Throwable throwable) {
                        callback.onError(throwable);
                    }
                });
    }

    public void registerApi(UserDTO userDTO, ApiCallback<Void> callback) {
        RegisterAPI registerAPI = retrofitService.getRetrofit().create(RegisterAPI.class);
        registerAPI.registerUser(userDTO)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(null);
                        } else {
                            Log.e("API_ERROR", "Code: " + response.code());
                            try {
                                if (response.errorBody() != null) {
                                    Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        callback.onError(throwable);
                    }
                });
    }

    public void AuthToken(AuthRequest authRequest, ApiCallback<Token> callback) {
        Log.d("AUTH_DEBUG", "Starting AuthToken request for email: " + authRequest.getEmail());
        AuthAPI authAPI = retrofitService.getRetrofit().create(AuthAPI.class);
        authAPI.generateToken(authRequest)
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Log.d("AUTH_DEBUG", "Response received. Code: " + response.code());
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("AUTH_DEBUG", "Login successful. Token received.");
                            Token token = response.body();
                            callback.onSuccess(token);
                        } else {
                            Log.e("AUTH_DEBUG", "Login failed. Code: " + response.code());
                            try {
                                if (response.errorBody() != null) {
                                    String errorBody = response.errorBody().string();
                                    Log.e("AUTH_DEBUG", "Error body: " + errorBody);
                                }
                            } catch (IOException e) {
                                Log.e("AUTH_DEBUG", "Error reading error body", e);
                            }
                            callback.onError(new Throwable("Login failed with code: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable throwable) {
                        Log.e("AUTH_DEBUG", "Network failure during login", throwable);
                        callback.onError(throwable);
                    }
                });
    }
    public void quizApi(QuizRequestDTO quizRequestDTO, ApiCallback<QuizDTO> callback) {
        QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);
        questionAPI.QuizGen(quizRequestDTO)
                .enqueue(new Callback<QuizDTO>() {
                    @Override
                    public void onResponse(Call<QuizDTO> call, Response<QuizDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(new Throwable("Failed to fetch quiz"));
                        }
                    }

                    @Override
                    public void onFailure(Call<QuizDTO> call, Throwable throwable) {
                        callback.onError(throwable);
                    }
                });
    }

}
