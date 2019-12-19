package com.github.lany192.box.http;


import android.text.TextUtils;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.utils.JsonUtils;
import com.github.lany192.box.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 200) {
                String data = jsonObject.getString("data");
                if (TextUtils.isEmpty(data)) {
                    onSuccess(msg, null);
                } else {
                    T bean = JsonUtils.json2object(data, getClz());
                    if (bean != null) {
                        onSuccess(msg, bean);
                    } else {
                        onFailure(601, new Throwable("数据解析失败"));
                        EventBus.getDefault().post(new NetWorkEvent(NetUtils.isNetWorkAvailable()));
                    }
                }
            } else {
                onFailure(code, new Throwable(msg));
            }
        } catch (JSONException e) {
            e.printStackTrace();
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