package com.lany.box.http;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class Request<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T bean);

    public abstract void onFailure(Throwable e);
}
