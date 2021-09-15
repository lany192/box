package com.github.lany192.box.sample.http;

import com.github.lany192.box.sample.bean.Result;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 接口回调封装
 */
public interface ApiCallback<T> extends Observer<Result<T>> {
    @Override
    default void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    default void onNext(Result<T> result) {
        if (result.getCode() == 0) {
            onSuccess(result.getMsg(), result.getData());
        } else {
            onFailure(result.getMsg(), result.getCode());
        }
    }

    @Override
    default void onError(Throwable e) {
        onFailure(e.getMessage(), 400);
    }

    @Override
    default void onComplete() {

    }

    /**
     * 请求成功
     */
    void onSuccess(String msg, T t);

    /**
     * 请求失败
     */
    void onFailure(String msg, int code);
}
