package com.example.android.mood.network;

public interface RxJava2ApiCallback<T> {

        void onSuccess(T t);

        void onFailed(Throwable throwable);
    }
}
