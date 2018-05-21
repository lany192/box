package com.lany.box.interfaces;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;

public class SimpleFileDownloadListener extends FileDownloadListener {

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    protected void completed(BaseDownloadTask task) {

    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {

    }

    @Override
    protected void warn(BaseDownloadTask task) {

    }
}
