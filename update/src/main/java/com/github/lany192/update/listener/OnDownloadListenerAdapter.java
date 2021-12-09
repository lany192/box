package com.github.lany192.update.listener;

import java.io.File;

public abstract class OnDownloadListenerAdapter implements OnDownloadListener {
    /**
     * 开始下载
     */
    @Override
    public void start() {

    }

    /**
     * 下载中
     *
     * @param max      总进度
     * @param progress 当前进度
     */
    @Override
    public void downloading(int max, int progress) {

    }

    /**
     * 下载完成
     *
     * @param apk 下载好的apk
     */
    @Override
    public void done(File apk) {

    }

    /**
     * 取消下载
     */
    @Override
    public void cancel() {

    }

    /**
     * 下载出错
     *
     * @param e 错误信息
     */
    @Override
    public void error(Exception e) {

    }
}
