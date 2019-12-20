package com.github.lany192.box.sample.http;

import com.github.lany192.box.sample.bean.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 接口回调封装
 */
public abstract class HttpCallback<T> implements Callback<Result<T>> {

    @Override
    public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
        if (response.isSuccessful()) {
            Result<T> result = response.body();
            if (result != null) {
                if (result.isSuccess()) {
                    onSuccess(result.getMsg(), result.getData());
                } else {
                    onFailure(result.getCode(), new Throwable(result.getMsg()));
                }
            } else {
                onFailure(400, new Throwable(response.message()));
            }
        } else {
            onFailure(400, new Throwable("HTTP status code:" + response.code() + "," + response.message()));
        }
    }

    @Override
    public void onFailure(Call<Result<T>> call, Throwable t) {
        onFailure(400, t);
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
