package com.github.lany192.box.http;


import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.bean.BaseBean;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.utils.JsonUtils;
import com.github.lany192.box.utils.NetUtils;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class Callback<T> implements Observer<String> {
    private final String TAG = this.getClass().getSimpleName();
    private Logger.Builder log = XLog.tag(TAG);

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(String json) {
        log.json(json);
        Type type = new TypeToken<BaseBean<T>>() {}.getType();
        BaseBean<T> bean = JsonUtils.json2object(json, type);
        if (bean != null) {
            if (bean.isSuccess()) {
                onSuccess(bean.getMsg(), bean.getData());
            } else {
                onFailure(bean.getCode(), new Throwable(bean.getMsg()));
            }
        } else {
            onFailure(601, new Throwable("数据解析失败"));
            EventBus.getDefault().post(new NetWorkEvent(NetUtils.isNetWorkAvailable()));
        }
    }

    @Override
    public void onError(Throwable e) {
        EventBus.getDefault().post(new NetWorkEvent(NetUtils.isNetWorkAvailable()));
        onFailure(602, e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(String msg, T data);

    public abstract void onFailure(int code, Throwable e);
}