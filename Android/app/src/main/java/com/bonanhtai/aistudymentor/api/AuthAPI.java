package com.bonanhtai.aistudymentor.api;

import com.bonanhtai.aistudymentor.model.AuthRequest;
import com.bonanhtai.aistudymentor.model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/auth/login")
    Call<Token> generateToken(@Body AuthRequest authRequest);
}
