package com.lany.box.http;


import com.lany.box.utils.JsonUtils;

import java.lang.reflect.ParameterizedType;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class Request<T> implements Observer<String> {
    private Class<T> clz;

    public Class<T> getClz() {
        if (clz == null) {
            clz = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        }
        return clz;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String json) {
        T bean = JsonUtils.json2object(getClz(), json);
        if (bean != null) {
            onSuccess(bean);
        } else {
            onFailure(new Throwable("数据解析失败"));
        }
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