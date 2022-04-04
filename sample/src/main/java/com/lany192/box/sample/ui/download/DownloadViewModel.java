package com.lany192.box.sample.ui.download;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.github.lany192.arch.utils.FileUtils;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownloadViewModel extends AndroidViewModel {
    private final TaskLiveData liveData = new TaskLiveData();
    private final DownloadContext downloadContext;

    public DownloadViewModel(Application application) {
        super(application);
        List<Task> taskItems = new ArrayList<>();
        taskItems.add(new Task("微信", "http://dldir1.qq.com/weixin/android/weixin6516android1120.apk"));
        taskItems.add(new Task("微信arm64", "https://dldir1.qq.com/weixin/android/weixin8016android2040_arm64.apk"));
        taskItems.add(new Task("流利说", "https://cdn.llscdn.com/yy/files/tkzpx40x-lls-LLS-5.7-785-20171108-111118.apk"));
        taskItems.add(new Task("支付宝", "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk"));
        taskItems.add(new Task("网易云音乐", "http://d1.music.126.net/dmusic/CloudMusic_official_4.3.2.468990.apk"));
        taskItems.add(new Task("企业微信", "https://dldir1.qq.com/foxmail/work_weixin/wxwork_android_2.4.5.5571_100001.apk"));
        taskItems.add(new Task("好游快爆", "https://d.3839app.net/video/hykb/HYKB15590220211203pc.apk"));

        DownloadContext.QueueSet queueSet = new DownloadContext.QueueSet();
        queueSet.setMinIntervalMillisCallbackProcess(500);
        queueSet.setPassIfAlreadyCompleted(false);
        queueSet.setParentPathFile(new File(FileUtils.getCacheDir(application), "download"));

        DownloadContext.Builder builder = queueSet.commit();
        List<DownloadTask> tasks = new ArrayList<>();
        for (Task item : taskItems) {
            DownloadTask downloadTask = builder.bind(item.getUrl());
            TaskUtils.INSTANCE.saveTaskName(downloadTask, item.getName());
            tasks.add(downloadTask);
        }
        downloadContext = builder.build();
        liveData.setTasks(tasks);
    }

    public TaskLiveData getItems() {
        return liveData;
    }

    private final DownloadListener downloadListener = new DownloadListener4WithSpeed() {
        @Override
        public void taskStart(@NonNull DownloadTask task) {
            Log.i("taskStart", "" + task.getTag());
            TaskUtils.INSTANCE.saveStatus(task, TaskStatus.TASKSTART);
            liveData.change(task);
        }

        @Override
        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
            Log.i("connectStart", "" + task.getFilename());
            liveData.change(task);
        }

        @Override
        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
            Log.i("connectEnd", "" + task.getFilename());
            liveData.change(task);
        }

        @Override
        public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
            Log.i("infoReady", "" + task.getFilename());
            TaskUtils.INSTANCE.saveTotal(task, info.getTotalLength());
            liveData.change(task);
        }

        @Override
        public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {
//                Log.i("progressBlock", "" + task.getFilename());
//                change(task);
        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
            long totalLength = TaskUtils.INSTANCE.getTotal(task);
            String readableOffset = Util.humanReadableBytes(currentOffset, true);
            String readableTotalLength = Util.humanReadableBytes(totalLength, true);
            String speed = taskSpeed.speed();
            float percent = currentOffset * 1.0f / totalLength * 100;

            Log.i("progress", readableOffset + "/" + readableTotalLength + ",速度：" + speed + ",进度：" + percent + "%");
            TaskUtils.INSTANCE.saveStatus(task, TaskStatus.PROGRESS);
            TaskUtils.INSTANCE.saveOffset(task, currentOffset);
            TaskUtils.INSTANCE.saveSpeed(task, speed);

            liveData.change(task);
        }

        @Override
        public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {
            Log.i("blockEnd", "" + info.toString());
//                change(task);
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
            Log.i("taskEnd", cause + "," + task.getFilename());
            if (realCause != null) {
                Log.i("taskEnd-error:", realCause.getMessage());
            }

            TaskUtils.INSTANCE.saveStatus(task, cause.toString());
            TaskUtils.INSTANCE.saveSpeed(task, "0KB/s");
            liveData.change(task);
        }
    };

    public void startAll() {
        downloadContext.startOnParallel(downloadListener);
    }

    public void start(DownloadTask task) {
        String status = TaskUtils.INSTANCE.getStatus(task);
//        if (status.equals(EndCause.COMPLETED.toString()) || status.equals(TaskStatus.PROGRESS)) {
//            //Log.w(TAG, "暂停....")
//            task.cancel();
//        } else if (status == STATUS_DELETE) {
//            //Log.w(TAG, "$STATUS_DELETE......")
//
//            deleteFile(task)
//            adapter?.setList(taskList)
//        } else {
//            //Log.w(TAG, "继续....")
//            task.enqueue(downloadListener);
//        }

        task.enqueue(downloadListener);
    }

    public void remove(DownloadTask task) {
        String status = TaskUtils.INSTANCE.getStatus(task);
        if (status.equals(TaskStatus.PROGRESS)) {
            task.cancel();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        downloadContext.stop();
    }
}
