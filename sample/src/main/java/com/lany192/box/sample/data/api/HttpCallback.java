package com.lany192.box.sample.data.api;

import com.hjq.toast.ToastUtils;

/**
 * 接口回调封装
 */
public interface HttpCallback<T> {

    /**
     * 请求成功
     */
    void onSuccess(String msg, T t);

    /**
     * 请求失败
     */
    default void onFailure(String msg, int code) {
        ToastUtils.show(msg);
    }
}
