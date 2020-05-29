package com.github.lany192.box.sample.http;

import com.github.lany192.box.sample.bean.Result;

import io.reactivex.rxjava3.observers.DisposableObserver;


/**
 * 接口回调封装
 */
public abstract class HttpCallback<T> extends DisposableObserver<Result<T>> {

    @Override
    public void onNext(Result<T> result) {
        if (result.isSuccess()) {
            onSuccess(result.getMsg(), result.getData());
        } else {
            onFailure(result.getCode(), new Throwable(result.getMsg()));
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailure(400, e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 请求成功
     */
    public abstract void onSuccess(String msg, T t);

    /**
     * 请求失败
     */
    public abstract void onFailure(int code, Throwable e);
}
