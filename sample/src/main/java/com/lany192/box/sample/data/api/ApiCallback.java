package com.lany192.box.sample.data.api;

import com.elvishew.xlog.XLog;
import com.lany192.box.sample.data.bean.ApiResult;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 接口回调封装
 */
public interface ApiCallback<T> extends Observer<ApiResult<T>> {
    @Override
    default void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    default void onNext(ApiResult<T> apiResult) {
        if (apiResult.getCode() == 0) {
            onSuccess(apiResult.getMsg(), apiResult.getData());
        } else {
            XLog.i("接口错误:" + apiResult.getMsg());
            onFailure(apiResult.getMsg(), apiResult.getCode());
        }
    }

    @Override
    default void onError(Throwable e) {
        XLog.i("接口错误:" + e.getMessage());
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
