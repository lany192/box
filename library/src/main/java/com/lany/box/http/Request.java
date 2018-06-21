package com.lany.box.http;


import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.utils.JsonUtils;

import java.lang.reflect.ParameterizedType;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class Request<T> implements Observer<String> {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    private Class<T> cls;

    public Class<T> getCls() {
        if (cls == null) {
            cls = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        }
        return cls;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String json) {
        log.i(json);
        T bean = JsonUtils.json2object(getCls(), json);
        if (bean != null) {
            onSuccess(bean);
        } else {
            onFailure(new Throwable("解析失败"));
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
