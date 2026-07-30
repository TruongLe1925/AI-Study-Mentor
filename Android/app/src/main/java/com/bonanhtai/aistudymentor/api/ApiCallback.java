package com.bonanhtai.aistudymentor.api;

public interface ApiCallback<T> {
    void onSuccess(T data);
    void onError(Throwable t);
}
