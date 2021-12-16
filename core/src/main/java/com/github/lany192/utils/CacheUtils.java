package com.github.lany192.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * 缓存工具
 */
public class CacheUtils {
    /**
     * 获取缓存大小
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(context.getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0KB";
    }

    /**
     * * 清除本应用所有的数据
     */
    public static void clean(Context context) {
        //清除沙盒缓存数据 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
        deleteFilesByDirectory(context.getCacheDir());
        //清除外部SD缓存数据 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
        //清除内部缓存数据 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
        //清除沙盒文件缓存 清除/data/data/com.xxx.xxx/files下的内容
        deleteFilesByDirectory(context.getFilesDir());
        //清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
//        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + " Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " TB";
    }
}