package com.github.lany192.arch.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.hjq.toast.Toaster;

import java.io.File;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class FileUtils {
    /**
     * 获取文件类型（文件名后缀作为识别标志）
     */
    public static String getFileMimeType(String fullPath) {
        String extension = getFileExtension(fullPath);
        if (!TextUtils.isEmpty(extension)) {
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            if (!TextUtils.isEmpty(mimeType)) {
                return mimeType;
            }
        }
        return "image/jpeg";
    }

    /**
     * 获取文件名的扩展名。
     */
    public static String getFileExtension(String fullPath) {
        if (TextUtils.isEmpty(fullPath)) {
            return "";
        }
        if (fullPath.lastIndexOf(".") != -1 && fullPath.lastIndexOf(".") != 0) {
            return fullPath.substring(fullPath.lastIndexOf(".") + 1);
        }
        return "";
    }

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
            Toaster.show(filePath + "路径不存在");
        }
    }

    /**
     * 读取文本内容
     */
    public static String readFromFile(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            try {
                //获取source对象
                Source read = Okio.source(file);
                BufferedSource bufferedSource = Okio.buffer(read);
                StringBuilder builder = new StringBuilder();
                String result;
                //循环读取数据
                while ((result = bufferedSource.readUtf8Line()) != null) {
                    builder.append(result);
                    builder.append("\n");
                }
                //关闭source
                bufferedSource.close();
                read.close();
                return builder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 保存内容到文件
     */
    public static void save2file(String filePath, String content) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            //获取sink对象
            Sink write = Okio.sink(file);
            //获取sink缓冲对象
            BufferedSink bufferedSink = Okio.buffer(write);
            //写入数据
            bufferedSink.writeUtf8(content);
            //关闭sink
            bufferedSink.close();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Uri getTempPicUri(Context context) {
        try {
            String imagePath = context.getCacheDir().getPath() + "/" + System.currentTimeMillis() + ".jpg";
            File file = new File(imagePath);
            if(!file.exists()){
                file.createNewFile();
            }
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
