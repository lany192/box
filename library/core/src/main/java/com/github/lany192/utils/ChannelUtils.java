package com.github.lany192.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.vasdolly.reader.ChannelReader;
import com.tencent.vasdolly.reader.IdValueReader;
import com.tencent.vasdolly.writer.ChannelWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

public class ChannelUtils {
    private static final String TAG = "ChannelUtils";
    private static String mChannelCache;

    /**
     * 获取写入了渠道信息的apk路径
     */
    public static String getChannelApkPath(Context context, String channel) {
        String apkPath = getApkPath(context);
        if (TextUtils.isEmpty(apkPath)) {
            return "";
        }
        String destFilePath = context.getCacheDir().getPath() + File.separator + "channel_" + channel + ".apk";
        boolean result = copyFile(apkPath, destFilePath);
        if (result) {
            try {
                File file = new File(destFilePath);
                ChannelWriter.removeChannelByV2(new File(apkPath), false);
                ChannelWriter.addChannelByV2(file, channel, false);
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        return destFilePath;
    }

    public static boolean copyFile(String srcFilePath, String destFilePath) {
        try {
            File srcFile = new File(srcFilePath);
            File destFile = new File(destFilePath);
            FileInputStream fileInputStream = new FileInputStream(srcFile);
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            fileInputStream.close();
            fileOutputStream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取渠道号
     */
    public static String getChannel(Context context) {
        if (mChannelCache == null) {
            String channel = getChannelByV2(context);
            if (channel == null) {
                channel = getChannelByV1(context);
            }
            mChannelCache = channel;
        }
        return mChannelCache;
    }

    /**
     * if apk use v2 signature , please use this method to get channel info
     *
     * @param context
     * @return
     */
    public static String getChannelByV2(Context context) {
        String apkPath = getApkPath(context);
        String channel = ChannelReader.getChannelByV2(new File(apkPath));
        Log.i(TAG, "getChannelByV2 , channel = " + channel);
        return channel;
    }

    /**
     * if apk only use v1 signature , please use this method to get channel info
     *
     * @param context
     * @return
     */
    public static String getChannelByV1(Context context) {
        String apkPath = getApkPath(context);
        String channel = ChannelReader.getChannelByV1(new File(apkPath));
        Log.i(TAG, "getChannelByV1 , channel = " + channel);
        return channel;
    }


    /**
     * get String value from apk by id in the v2 signature mode
     *
     * @param context
     * @param id
     * @return
     */
    public static String getStringValueById(Context context, int id) {
        String apkPath = getApkPath(context);
        String value = IdValueReader.getStringValueById(new File(apkPath), id);
        Log.i(TAG, "id = " + id + " , value = " + value);
        return value;
    }

    /**
     * get byte[] from apk by id in the v2 signature mode
     *
     * @param context
     * @param id
     * @return
     */
    public static byte[] getByteValueById(Context context, int id) {
        String apkPath = getApkPath(context);
        return IdValueReader.getByteValueById(new File(apkPath), id);
    }

    /**
     * find all Id-Value Pair from Apk in the v2 signature mode
     *
     * @param context
     * @return
     */
    public static Map<Integer, ByteBuffer> getAllIdValueMap(Context context) {
        String apkPath = getApkPath(context);
        return IdValueReader.getAllIdValueMap(new File(apkPath));
    }

    /**
     * 获取已安装的APK路径
     *
     * @param context
     * @return
     */
    private static String getApkPath(Context context) {
        String apkPath = null;
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo == null) {
                return null;
            } else {
                apkPath = applicationInfo.sourceDir;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return apkPath;
    }
}
