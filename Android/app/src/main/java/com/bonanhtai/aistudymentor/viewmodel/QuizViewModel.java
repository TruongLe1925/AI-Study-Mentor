package com.bonanhtai.aistudymentor.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bonanhtai.aistudymentor.api.ApiCallback;
import com.bonanhtai.aistudymentor.api.SubjectAPI;
import com.bonanhtai.aistudymentor.model.QuizDTO;
import com.bonanhtai.aistudymentor.model.QuizRequestDTO;
import com.bonanhtai.aistudymentor.model.SubjectDTO;
import com.bonanhtai.aistudymentor.model.Token;
import com.bonanhtai.aistudymentor.repository.CallAPI;
import com.bonanhtai.aistudymentor.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizViewModel extends AndroidViewModel {

    private final CallAPI repository;
    private final RetrofitService retrofitService;

    private final MutableLiveData<List<SubjectDTO>> _subjects = new MutableLiveData<>();
    public LiveData<List<SubjectDTO>> getSubjects() {
        return _subjects;
    }

    private final MutableLiveData<QuizDTO> _quiz = new MutableLiveData<>();
    public LiveData<QuizDTO> getQuiz() {
        return _quiz;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> getError() {
        return _error;
    }

    public QuizViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CallAPI(application);
        this.retrofitService = new RetrofitService(application);
    }

    public void loadSubjects() {
        _isLoading.setValue(true);
        SubjectAPI subjectAPI = retrofitService.getRetrofit().create(SubjectAPI.class);
        subjectAPI.GetAllEmployee().enqueue(new Callback<List<SubjectDTO>>() {
            @Override
            public void onResponse(Call<List<SubjectDTO>> call, Response<List<SubjectDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _subjects.postValue(response.body());
                } else {
                    _error.postValue("Failed to load subjects");
                }
                _isLoading.postValue(false);
            }

            @Override
            public void onFailure(Call<List<SubjectDTO>> call, Throwable t) {
                _error.postValue(t.getMessage());
                _isLoading.postValue(false);
            }
        });
    }

    public void generateQuiz(String subject) {
        _isLoading.setValue(true);
        QuizRequestDTO request = new QuizRequestDTO(subject);

        // Retrieve token from SharedPreferences
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwt_token", null);

        if (jwtToken != null) {
            Token token = new Token();
            token.setAccessToken(jwtToken);
            request.setToken(token);
        }

        repository.quizApi(request, new ApiCallback<QuizDTO>() {
            @Override
            public void onSuccess(QuizDTO data) {
                _quiz.postValue(data);
                _isLoading.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
                _error.postValue(t.getMessage());
                _isLoading.postValue(false);
            }
        });
    }
}
