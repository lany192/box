package com.github.lany192.update.manager;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.update.listener.OnDownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.io.File;
import java.util.List;
import java.util.Map;

public class HttpDownloadManager {
    private final String downloadPath;
    private DownloadTask downloadTask;
    private long totalLength;

    public HttpDownloadManager(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void download(String apkUrl, String apkName, OnDownloadListener listener) {
        downloadTask = new DownloadTask.Builder(apkUrl, Uri.fromFile(new File(downloadPath)))
                .setFilenameFromResponse(true)
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(50)
                // ignore the same task has already completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();
        downloadTask.enqueue(new DownloadListener4WithSpeed() {
            @Override
            public void taskStart(@NonNull DownloadTask task) {
                if (listener != null) {
                    listener.start();
                }
            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

            }

            @Override
            public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
                totalLength = info.getTotalLength();
            }

            @Override
            public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {

            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
                if (listener != null) {
                    listener.downloading(100, (int) (currentOffset * 1.0f / totalLength * 100));
                }
            }

            @Override
            public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
                if (cause == EndCause.CANCELED) {
                    if (listener != null) {
                        listener.cancel();
                    }
                } else if (cause == EndCause.COMPLETED) {
                    if (listener != null) {
                        listener.done(task.getFile());
                    }
                } else if (cause == EndCause.ERROR) {
                    if (listener != null) {
                        listener.error(realCause);
                    }
                }
            }
        });
    }

    public void cancel() {
        downloadTask.cancel();
    }

    public void release() {
        downloadTask.cancel();
        downloadTask = null;
    }
}
