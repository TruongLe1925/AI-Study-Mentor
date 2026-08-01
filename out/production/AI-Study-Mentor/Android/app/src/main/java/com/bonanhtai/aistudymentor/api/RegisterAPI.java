package com.bonanhtai.aistudymentor.api;

import com.bonanhtai.aistudymentor.model.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterAPI {
    @POST("api/auth/register")
    Call<Void> registerUser(@Body UserDTO user);
}
