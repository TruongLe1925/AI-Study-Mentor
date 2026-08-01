package com.bonanhtai.aistudymentor.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bonanhtai.aistudymentor.api.ApiCallback;
import com.bonanhtai.aistudymentor.model.AnswerDTO;
import com.bonanhtai.aistudymentor.model.AskDTO;
import com.bonanhtai.aistudymentor.repository.CallAPI;

import java.io.File;

public class ChatViewModel extends AndroidViewModel {

    private final CallAPI repository;

    private final MutableLiveData<AnswerDTO> _answer = new MutableLiveData<>();
    public LiveData<AnswerDTO> getAnswer() {
        return _answer;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> getError() {
        return _error;
    }

    private final MutableLiveData<File> _selectedImage = new MutableLiveData<>();
    public LiveData<File> getSelectedImage() {
        return _selectedImage;
    }

    public void setSelectedImage(File file) {
        _selectedImage.setValue(file);
    }

    public ChatViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CallAPI(application);
    }

    public void sendQuestion(AskDTO askDTO, File imageFile) {
        _isLoading.setValue(true);

        repository.askApi(askDTO, imageFile, new ApiCallback<AnswerDTO>() {
            @Override
            public void onSuccess(AnswerDTO data) {
                _answer.postValue(data);
                _isLoading.postValue(false);
                _selectedImage.postValue(null); // Clear image after success
            }

            @Override
            public void onError(Throwable t) {
                _error.postValue(t.getMessage());
                _isLoading.postValue(false);
            }
        });
    }
}
