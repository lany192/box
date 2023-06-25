package cn.smallplants.client.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.meituan.android.walle.WalleChannelReader;


public class ChannelUtils {

    /**
     * 获取渠道标识.
     */
    public static String getChannel(Context context, String defaultValue) {
        String channel = WalleChannelReader.getChannel(context);
        if (TextUtils.isEmpty(channel)) {
            channel = defaultValue;
        }
        Log.i("ChannelUtils", "当前apk渠道号: " + channel);
        return channel;
    }

    /**
     * 获取邀请包信息
     */
    public static String getApkExtraInfo(Context context) {
        //这个key值需要和服务端约定，服务端生成邀请包的时候需要定的key要和客户端的一致
        String key = "extra";
        String value = WalleChannelReader.get(context, key);
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }
}
