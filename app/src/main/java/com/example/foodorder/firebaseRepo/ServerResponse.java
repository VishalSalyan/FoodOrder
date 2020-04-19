package com.example.foodorder.firebaseRepo;

public interface ServerResponse<T> {
    void onSuccess(T body);

    void onFailure(Throwable error);
}
