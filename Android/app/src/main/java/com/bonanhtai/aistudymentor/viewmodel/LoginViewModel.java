package com.bonanhtai.aistudymentor.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bonanhtai.aistudymentor.api.ApiCallback;
import com.bonanhtai.aistudymentor.model.AuthRequest;
import com.bonanhtai.aistudymentor.model.Token;
import com.bonanhtai.aistudymentor.repository.CallAPI;

public class LoginViewModel extends AndroidViewModel {

    private final CallAPI repository;

    private final MutableLiveData<String> _token = new MutableLiveData<>();
    public LiveData<String> getToken() {
        return _token;
    }

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CallAPI(application);
    }

    public void login(AuthRequest request) {
        android.util.Log.d("AUTH_DEBUG", "ViewModel: Login process started for " + request.getEmail());
        _isLoading.setValue(true);
        repository.AuthToken(request, new ApiCallback<Token>() {
            @Override
            public void onSuccess(Token token) {
                if (token != null && token.getAccessToken() != null) {
                    android.util.Log.d("AUTH_DEBUG", "ViewModel: Login success. Token received.");
                    _token.postValue(token.getAccessToken());
                } else {
                    _errorMessage.postValue("Invalid token received from server");
                }
                _isLoading.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
                android.util.Log.e("AUTH_DEBUG", "ViewModel: Login error: " + t.getMessage());
                _errorMessage.postValue(t.getMessage());
                _isLoading.postValue(false);
            }
        });
    }
}
