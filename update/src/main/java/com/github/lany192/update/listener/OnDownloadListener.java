package com.github.lany192.update.listener;

import java.io.File;

public interface OnDownloadListener {

    /**
     * 开始下载
     */
    default void start(){}

    /**
     * 下载中
     *
     * @param max      总进度
     * @param progress 当前进度
     */
    void downloading(int max, int progress);

    /**
     * 下载完成
     *
     * @param apk 下载好的apk
     */
    default void done(File apk){

    }

    /**
     * 取消下载
     */
    default void cancel(){

    }

    /**
     * 下载出错
     *
     * @param e 错误信息
     */
    default void error(Exception e){

    }
}
