package com.github.lany192.box.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.hjq.toast.ToastUtils;

import java.io.File;

public class FileUtils {

    /**
     * 获取APP的工作路径
     */
    public static File getDiskAppDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getParentFile();
        } else {
            return context.getCacheDir().getParentFile();
        }
    }

    /**
     * 获取缓存路径
     */
    public static File getCacheDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir();
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 根据传入的uniqueFileDirName获取硬盘缓存夹的路径地址
     *
     * @param context
     * @param uniqueFileDirName
     * @return
     */
    public static File getCacheDir(Context context, String uniqueFileDirName) {
        String cacheDirPath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cacheDirPath = context.getExternalCacheDir().getPath();
        } else {
            cacheDirPath = context.getCacheDir().getPath();
        }
        return new File(cacheDirPath + File.separator + uniqueFileDirName);
    }

    /**
     * 根据传入的uniqueFileDirName获取硬盘文件夹的路径地址
     *
     * @param context
     * @param uniqueFileDirName
     * @return
     */
    public static File getDiskFilesDir(Context context, String uniqueFileDirName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalFilesDir(uniqueFileDirName);
        } else {
            String cachePath = context.getFilesDir().getPath();
            return new File(cachePath + File.separator + uniqueFileDirName);
        }
    }

    /**
     * 安装新版本
     */
    public static void installNewVersion(Context context, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {
            ToastUtils.show(filePath + "路径不存在");
        }
    }
}
