package com.github.lany192.arch.items;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ViewState {
    /**
     * 显示内容
     */
    int CONTENT_VIEW = 0;
    /**
     * 显示错误
     */
    int ERROR_VIEW = 1;
    /**
     * 显示空数据
     */
    int EMPTY_VIEW = 2;
    /**
     * 显示加载
     */
    int LOADING_VIEW = 3;
    /**
     * 显示无网络
     */
    int NETWORK_VIEW = 4;
    /**
     * 显示加载对话框
     */
    int SHOW_LOADING_DIALOG = 5;
    /**
     * 取消加载对话框
     */
    int CANCEL_LOADING_DIALOG = 6;
}
