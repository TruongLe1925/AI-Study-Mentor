package com.bonanhtai.aistudymentor.api;

import com.bonanhtai.aistudymentor.model.Subject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SubjectAPI {
    @GET("api/subject/allsubjects")
    Call<List<Subject>> GetAllEmployee();
}
