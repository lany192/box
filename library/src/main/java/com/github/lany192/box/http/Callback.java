package com.github.lany192.box.http;


import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.utils.JsonUtils;

import java.lang.reflect.ParameterizedType;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class Callback<T> implements Observer<String> {
    private final String TAG = this.getClass().getSimpleName();
    private Logger.Builder log = XLog.tag(TAG);
    private Class<T> clz;

    public Class<T> getClz() {
        if (clz == null) {
            clz = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        }
        return clz;
    }

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(String json) {
        log.json(json);
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