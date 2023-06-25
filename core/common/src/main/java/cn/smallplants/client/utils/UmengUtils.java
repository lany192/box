package cn.smallplants.client.utils;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.smallplants.client.App;
import cn.smallplants.client.common.Constants;

/**
 * 代表友盟SDK工具类。
 */
public class UmengUtils {

    /**
     * 预初始化，已添加子进程中初始化sdk。
     * 使用场景：用户未同意隐私政策协议授权时，延迟初始化
     */
    public static void preInit(Context context) {
        UMConfigure.preInit(context, Constants.UMENG_APP_KEY, App.getConfig().getChannel());
    }

    /**
     * 初始化友盟统计和推送
     */
    public static void init(Context context) {
        //建议子线程中初始化SDK（启动优化）
        new Thread(() -> {
            //微信设置
            PlatformConfig.setWeixin(Constants.WECHAT_APP_ID, Constants.WECHAT_APP_SECRET);
            PlatformConfig.setWXFileProvider(App.getConfig().getFileProvider());
            //QQ设置
            PlatformConfig.setQQZone(Constants.QQ_APP_ID, Constants.QQ_APP_KEY);
            PlatformConfig.setQQFileProvider(App.getConfig().getFileProvider());
            //微博设置
            PlatformConfig.setSinaWeibo(Constants.WEIBO_APP_ID, Constants.WEIBO_APP_KEY, Constants.WEIBO_REDIRECT_URL);
            PlatformConfig.setSinaFileProvider(App.getConfig().getFileProvider());
            //设置LOG开关，默认为false
            UMConfigure.setLogEnabled(true);
            //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
            UMConfigure.init(context, Constants.UMENG_APP_KEY, App.getConfig().getChannel(), UMConfigure.DEVICE_TYPE_PHONE, Constants.UMENG_MESSAGE_SECRET);
            //统计SDK是否支持采集在子进程中打点的自定义事件，默认不支持
            UMConfigure.setProcessEvent(true);//支持多进程打点
        }).start();
    }
}
