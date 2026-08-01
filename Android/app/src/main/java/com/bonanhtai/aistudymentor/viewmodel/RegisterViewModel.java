package com.bonanhtai.aistudymentor.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bonanhtai.aistudymentor.api.RegisterAPI;
import com.bonanhtai.aistudymentor.model.UserDTO;
import com.bonanhtai.aistudymentor.retrofit.RetrofitService;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> _isSuccess = new MutableLiveData<>();
    public LiveData<Boolean> getIsSuccess() {
        return _isSuccess;
    }

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    private final RetrofitService retrofitService;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.retrofitService = new RetrofitService(application);
    }

    public void register(UserDTO userDTO) {
        _isLoading.setValue(true);
        RegisterAPI registerAPI = retrofitService.getRetrofit().create(RegisterAPI.class);
        registerAPI.registerUser(userDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                _isLoading.postValue(false);
                if (response.isSuccessful()) {
                    _isSuccess.postValue(true);
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                _isLoading.postValue(false);
                _errorMessage.postValue("Network error: " + t.getMessage());
            }
        });
    }

    private void handleError(Response<Void> response) {
        try {
            if (response.errorBody() != null) {
                String errorJson = response.errorBody().string();
                JSONObject jsonObject = new JSONObject(errorJson);
                
                if (jsonObject.length() > 0) {
                    // 1. Check for custom exception "message" field first
                    if (jsonObject.has("message")) {
                        _errorMessage.postValue(jsonObject.getString("message"));
                    } else {
                        // 2. Fallback to multi-field validation errors
                        StringBuilder fullErrorMessage = new StringBuilder();
                        java.util.Iterator<String> keys = jsonObject.keys();
                        
                        while (keys.hasNext()) {
                            String key = keys.next();
                            String message = jsonObject.getString(key);
                            fullErrorMessage.append("• ").append(message).append("\n");
                        }
                        
                        _errorMessage.postValue(fullErrorMessage.toString().trim());
                    }
                } else {
                    _errorMessage.postValue("Registration failed. Please try again.");
                }
            } else {
                _errorMessage.postValue("An unknown error occurred.");
            }
        } catch (Exception e) {
            _errorMessage.postValue("Error parsing server response.");
        }
    }
}
