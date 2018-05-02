package com.lany.box.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by user on 2016/2/25.
 */
public class FileUtils {

    /**
     * 获取APP的工作路径
     *
     * @param context
     * @return
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
     * 根据传入的uniqueFileDirName获取硬盘缓存夹的路径地址
     *
     * @param context
     * @param uniqueFileDirName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueFileDirName) {
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
            Toast.makeText(context, filePath + "路径不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
