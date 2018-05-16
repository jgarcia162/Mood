package com.example.android.mood.network;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaCallHelper {
    public static <T> Disposable call(Observable<T> observable, final RxJava2ApiCallback<T> rxJavaCallback) {
        if (observable == null) {
            throw new IllegalArgumentException("Observable must not be null.");
        }

        if (rxJavaCallback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable != null) {
                            rxJavaCallback.onFailed(throwable);
                        } else {
                            rxJavaCallback.onFailed(new Throwable("Something went wrong"));
                        }
                    }
                });
    }
}
