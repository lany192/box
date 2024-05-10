package com.github.lany192.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.vasdolly.reader.ChannelReader;
import com.tencent.vasdolly.reader.IdValueReader;
import com.tencent.vasdolly.writer.ChannelWriter;

import java.io.File;
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
        boolean result = FileUtils.copyFile(apkPath, destFilePath);
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

    /**
     * 获取渠道号
     */
    public static String getChannel(Context context) {
        if (mChannelCache == null) {
            String apkPath = getApkPath(context);
            String channel = getChannelByPath(apkPath);
            Log.i(TAG, "apkPath:" + apkPath + "channel: " + channel);
            mChannelCache = channel;
        }
        return mChannelCache;
    }

    /**
     * 获取渠道号
     */
    public static String getChannelByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                String channel = ChannelReader.getChannelByV2(file);
                if (TextUtils.isEmpty(channel)) {
                    channel = ChannelReader.getChannelByV1(file);
                }
                if (!TextUtils.isEmpty(channel)) {
                    return channel;
                }
            }
        }
        return "";
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
