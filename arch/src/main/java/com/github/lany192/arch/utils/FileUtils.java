package com.github.lany192.arch.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.hjq.toast.ToastUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    /**
     * 读取文本内容
     */
    public static String readTextByPath(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            StringBuilder builder = new StringBuilder();
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String tmp;
                while ((tmp = br.readLine()) != null) {
                    builder.append(tmp);
                    builder.append("\n");
                }
                return builder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 根据日期读取日志内容
     */
    public static String getLogPathByDate(Context context, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String fileName = sdf.format(date);
        String path = context.getFilesDir().getPath() + "/logs/app_log_" + fileName + ".log";
        return readTextByPath(path);
    }
}
