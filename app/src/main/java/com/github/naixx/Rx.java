package com.github.naixx;

import java.util.concurrent.Executors;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Rx {

    public static <T> Observable.Transformer<T, T> async2ui() {
        return observable -> observable.subscribeOn(Schedulers.from(Executors.newCachedThreadPool()))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
