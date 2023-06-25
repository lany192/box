package cn.smallplants.client.utils;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.github.lany192.arch.utils.StorageUtils;
import com.github.lany192.utils.MD5Utils;
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

import cn.smallplants.client.App;
import cn.smallplants.client.common.R;
import cn.smallplants.client.model.v4.VersionInfo;


public class ApkUtils {
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "ApkUtils";
    private static long totalLength = 0;

    public static void startDownloadApk(Context context, VersionInfo info) {
        Log.i(TAG, "下载更新地址：" + info.getApkUrl());
        String apkName = "TDR_" + MD5Utils.md5(info.getApkUrl()) + ".apk";
        String savePath = StorageUtils.getCacheDirectory(context)
                + File.separator + apkName;
        Log.i(TAG, "下载保存地址：" + savePath);
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("下载进度");
        dialog.setIndeterminate(false);
        dialog.setProgress(100);
        dialog.setCancelable(true);
        dialog.show();

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
        builder.setProgress(100, 0, false);
        builder.setAutoCancel(true);

        DownloadTask downloadTask = new DownloadTask.Builder(info.getApkUrl(), Uri.fromFile(new File(savePath)))
                .setFilename(apkName)
//                .setFilenameFromResponse(true)
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(50)
                // ignore the same task has already completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();

        downloadTask.enqueue(new DownloadListener4WithSpeed() {
            @Override
            public void taskStart(@NonNull DownloadTask task) {
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
                int progress = (int) (currentOffset * 1.0f / totalLength * 100);
                Log.i(TAG, "下载进度：" + progress + "%");
                dialog.setProgress(progress);

                builder.setProgress(100, progress, false);
                manager.notify(NOTIFICATION_ID, builder.build());
                //下载进度提示
                builder.setContentText("下载" + progress + "%");
            }

            @Override
            public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
                if (cause == EndCause.CANCELED) {
                    dialog.cancel();
                } else if (cause == EndCause.COMPLETED) {
                    installApk(context, task.getFile().getPath());
                } else if (cause == EndCause.ERROR) {
                    dialog.cancel();
                }
            }
        });

    }

    /**
     * 安装apk文件
     * <p>
     * 注意：需要android.permission.REQUEST_INSTALL_PACKAGES权限，路径需要配置可访问
     */
    private static void installApk(Context context, String apkPath) {

        File file = new File(apkPath);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, App.getConfig().getAppId() + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
