package com.github.lany192.box;

import android.Manifest;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.log.LogFileFormat;
import com.github.lany192.box.log.LogFileNameGenerator;
import com.github.lany192.box.utils.DensityUtils;
import com.github.lany192.box.utils.NetUtils;
import com.github.lany192.box.utils.OtherUtils;
import com.github.lany192.box.utils.PermissionUtils;
import com.github.lany192.box.utils.PhoneUtils;
import com.hjq.toast.IToastStyle;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;


public class Box {
    private volatile static Box instance;
    private final String TAG = "Box";
    private Context context;

    private Box() {

    }

    public static Box get() {
        if (instance == null) {
            synchronized (Box.class) {
                if (instance == null) {
                    instance = new Box();
                }
            }
        }
        return instance;
    }

    public void init(Application ctx) {
        init(ctx, false);
    }

    public void init(Application ctx, boolean debug) {
        Context app = ctx.getApplicationContext();
        if (app == null) {
            this.context = ctx;
        } else {
            this.context = ((Application) app).getBaseContext();
        }
        initToast(ctx);
        initLog(debug);
        initCatchException();
        initRefreshView();
        registerNetwork();
        OtherUtils.closeAndroidPWarningDialog(debug);
    }

    private void initToast(Application ctx) {
        ToastUtils.init(ctx);
        ToastUtils.initStyle(new IToastStyle() {

            @Override
            public int getGravity() {
                return Gravity.CENTER;
            }

            @Override
            public int getXOffset() {
                return 0;
            }

            @Override
            public int getYOffset() {
                return 0;
            }

            @Override
            public int getZ() {
                return 4;
            }

            @Override
            public int getCornerRadius() {
                return DensityUtils.dp2px(5);
            }

            @Override
            public int getBackgroundColor() {
                return 0xa0000000;
            }

            @Override
            public int getTextColor() {
                return Color.WHITE;
            }

            @Override
            public float getTextSize() {
                return DensityUtils.sp2px(14);
            }

            @Override
            public int getMaxLines() {
                return 5;
            }

            @Override
            public int getPaddingStart() {
                return DensityUtils.dp2px(16);
            }

            @Override
            public int getPaddingTop() {
                return getPaddingStart();
            }

            @Override
            public int getPaddingEnd() {
                return getPaddingStart();
            }

            @Override
            public int getPaddingBottom() {
                return getPaddingStart();
            }
        });
    }

    private void registerNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && PermissionUtils.checkPermission(getContext(), Manifest.permission.CHANGE_NETWORK_STATE)
                && PermissionUtils.checkPermission(getContext(), Manifest.permission.WRITE_SETTINGS)) {
            ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            manager.requestNetwork(new NetworkRequest.Builder().build(),
                    new ConnectivityManager.NetworkCallback() {

                        @Override
                        public void onLost(Network network) {
                            super.onLost(network);
                            EventBus.getDefault().post(new NetWorkEvent(false));
                        }

                        @Override
                        public void onAvailable(Network network) {
                            super.onAvailable(network);
                            EventBus.getDefault().post(new NetWorkEvent(true));
                        }
                    });
        } else {
            getContext().registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    EventBus.getDefault().post(new NetWorkEvent(NetUtils.isNetWorkAvailable()));
                }
            }, new IntentFilter("android.net.conn.CONNECTIVTY_CHANGE"));
        }
    }

    private void initCatchException() {
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> {
            XLog.tag(TAG).i("手机基本信息:" + PhoneUtils.getBaseInfo());
            XLog.tag(TAG).e(e.getLocalizedMessage());
            XLog.tag(TAG).st(10).e(TAG, "程序崩溃退出", e);
            Log.e(TAG, "程序崩溃退出", e);
        });
    }

    private void initRefreshView() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.refresh_head_background, R.color.refresh_head_text_color);//全局设置主题颜色
            return new ClassicsHeader(context)
                    .setArrowResource(R.drawable.vector_arrow_gray);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    private void initLog(boolean debug) {
        LogConfiguration config = new LogConfiguration
                .Builder()
                .logLevel(LogLevel.ALL)
                .tag("XLog")
                .build();
        String logPath = context.getFilesDir().getPath() + "/log/";
        Log.i(TAG, "初始化日志文件路径:" + logPath);
        Printer filePrinter = new FilePrinter
                .Builder(logPath)
                .fileNameGenerator(new LogFileNameGenerator())
                .flattener(new LogFileFormat())
                .build();
        if (debug) {
            XLog.init(config, new AndroidPrinter(), filePrinter);
        } else {
            XLog.init(config, filePrinter);
        }
    }

    public Context getContext() {
        return context;
    }
}
