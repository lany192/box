package com.github.lany192.update.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.lany192.log.LogUtils;
import com.github.lany192.log.XLog;
import com.github.lany192.update.R;
import com.github.lany192.update.config.UpdateConfig;
import com.github.lany192.update.listener.OnDownloadListener;
import com.github.lany192.update.manager.HttpDownloadManager;
import com.github.lany192.update.manager.UpdateManager;
import com.github.lany192.update.utils.ApkUtil;
import com.github.lany192.update.utils.Constant;
import com.github.lany192.update.utils.FileUtil;
import com.github.lany192.update.utils.NotificationUtil;

import java.io.File;
import java.util.List;

public final class DownloadService extends Service implements OnDownloadListener {
    private final XLog log = LogUtils.tag(getClass().getSimpleName());
    private int smallIcon;
    private String apkUrl;
    private String apkName;
    private String downloadPath;
    private List<OnDownloadListener> listeners;
    private boolean showNotification;
    private boolean showBgdToast;
    private boolean jumpInstallPage;
    private int lastProgress;
    private UpdateManager updateManager;
    private HttpDownloadManager httpManager;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DownloadService.this, R.string.background_downloading, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    for (OnDownloadListener listener : listeners) {
                        listener.start();
                    }
                    break;
                case 2:
                    for (OnDownloadListener listener : listeners) {
                        listener.downloading(msg.arg1, msg.arg2);
                    }
                    break;
                case 3:
                    for (OnDownloadListener listener : listeners) {
                        listener.done((File) msg.obj);
                    }
                    //执行了完成开始释放资源
                    releaseResources();
                    break;
                case 4:
                    for (OnDownloadListener listener : listeners) {
                        listener.cancel();
                    }
                    break;
                case 5:
                    for (OnDownloadListener listener : listeners) {
                        listener.error((Exception) msg.obj);
                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_STICKY;
        }
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        updateManager = UpdateManager.getInstance();
        if (updateManager == null) {
            log.d("init DownloadManager.getInstance() = null ,请先调用 getInstance(Context context) !");
            return;
        }
        apkUrl = updateManager.getApkUrl();
        apkName = updateManager.getApkName();
        downloadPath = updateManager.getDownloadPath();
        smallIcon = updateManager.getSmallIcon();
        //创建apk文件存储文件夹
        FileUtil.createDirDirectory(downloadPath);

        UpdateConfig configuration = updateManager.getConfiguration();
        listeners = configuration.getOnDownloadListener();
        showNotification = configuration.isShowNotification();
        showBgdToast = configuration.isShowBgdToast();
        jumpInstallPage = configuration.isJumpInstallPage();
        //获取app通知开关是否打开
        boolean enable = NotificationUtil.notificationEnable(this);
        log.d(enable ? "应用的通知栏开关状态：已打开" : "应用的通知栏开关状态：已关闭");
        if (checkApkMD5()) {
            log.d("文件已经存在直接进行安装");
            //直接调用完成监听即可
            done(FileUtil.createFile(downloadPath, apkName));
        } else {
            log.d("文件不存在开始下载");
            download(configuration);
        }
    }

    /**
     * 校验Apk是否已经下载好了，不重复下载
     *
     * @return 是否下载完成
     */
    private boolean checkApkMD5() {
        if (FileUtil.fileExists(downloadPath, apkName)) {
            String fileMD5 = FileUtil.getFileMD5(FileUtil.createFile(downloadPath, apkName));
            return fileMD5.equalsIgnoreCase(updateManager.getApkMD5());
        }
        return false;
    }

    /**
     * 获取下载管理者
     */
    private synchronized void download(UpdateConfig configuration) {
        if (updateManager.isDownloading()) {
            log.e("download: 当前正在下载，请务重复下载！");
            return;
        }
        httpManager = configuration.getHttpManager();
        //使用自己的下载
        if (httpManager == null) {
            httpManager = new HttpDownloadManager(downloadPath);
            configuration.setHttpManager(httpManager);
        }
        //如果用户自己定义了下载过程
        httpManager.download(apkUrl, apkName, this);
        updateManager.setState(true);
    }

    @Override
    public void start() {
        if (showNotification) {
            if (showBgdToast) {
                handler.sendEmptyMessage(0);
            }
            String startDownload = getResources().getString(R.string.start_download);
            String startDownloadHint = getResources().getString(R.string.start_download_hint);
            NotificationUtil.showNotification(this, smallIcon, startDownload, startDownloadHint);
        }
        handler.sendEmptyMessage(1);
    }

    @Override
    public void downloading(int max, int progress) {
        log.i("max: " + max + " --- progress: " + progress);
        if (showNotification) {
            //优化通知栏更新，减少通知栏更新次数
            int curr = (int) (progress / (double) max * 100.0);
            if (curr != lastProgress) {
                lastProgress = curr;
                String downloading = getResources().getString(R.string.start_downloading);
                String content = curr < 0 ? "" : curr + "%";
                NotificationUtil.showProgressNotification(this, smallIcon, downloading,
                        content, max == -1 ? -1 : 100, curr);
            }
        }
        handler.obtainMessage(2, max, progress).sendToTarget();
    }

    @Override
    public void done(File apk) {
        log.d("done: 文件已下载至" + apk.toString());
        updateManager.setState(false);
        //如果是android Q（api=29）及其以上版本showNotification=false也会发送一个下载完成通知
        if (showNotification || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String downloadCompleted = getResources().getString(R.string.download_completed);
            String clickHint = getResources().getString(R.string.click_hint);
            NotificationUtil.showDoneNotification(this, smallIcon, downloadCompleted,
                    clickHint, Constant.AUTHORITIES, apk);
        }
        if (jumpInstallPage) {
            ApkUtil.installApk(this, Constant.AUTHORITIES, apk);
        }
        //如果用户设置了回调 则先处理用户的事件 在执行自己的
        handler.obtainMessage(3, apk).sendToTarget();
    }

    @Override
    public void cancel() {
        updateManager.setState(false);
        if (showNotification) {
            NotificationUtil.cancelNotification(this);
        }
        handler.sendEmptyMessage(4);
    }

    @Override
    public void error(Exception e) {
        log.e("error: " + e);
        updateManager.setState(false);
        if (showNotification) {
            String downloadError = getResources().getString(R.string.download_error);
            String conDownloading = getResources().getString(R.string.continue_downloading);
            NotificationUtil.showErrorNotification(this, smallIcon, downloadError, conDownloading);
        }
        handler.obtainMessage(5, e).sendToTarget();
    }

    /**
     * 下载完成释放资源
     */
    private void releaseResources() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (httpManager != null) {
            httpManager.release();
        }
        stopSelf();
        updateManager.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
