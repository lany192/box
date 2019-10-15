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
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.helper.SPHelper;
import com.github.lany192.box.utils.LogFileFormat;
import com.github.lany192.box.utils.LogFileNameGenerator;
import com.github.lany192.box.utils.NetUtils;
import com.github.lany192.box.utils.PermissionUtils;
import com.github.lany192.box.utils.PhoneUtils;
import com.hjq.toast.IToastStyle;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Box {
    private final String TAG = "Box";
    private Context context;
    private volatile static Box instance;

    private Box() {

    }

    public static Box of() {
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
        SPHelper.of().init(ctx);
        initLog(debug);
        initCatchException();
        initRefreshView();
        registerNetwork();
        closeAndroidPWarningDialog(debug);
    }

    private void closeAndroidPWarningDialog(boolean debug) {
        if (debug && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                Class aClass = Class.forName("android.content.pm.PackageParser$Package");
                Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Class cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                return 5;
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
                return 14;
            }

            @Override
            public int getMaxLines() {
                return 5;
            }

            @Override
            public int getPaddingLeft() {
                return 16;
            }

            @Override
            public int getPaddingTop() {
                return getPaddingLeft();
            }

            @Override
            public int getPaddingRight() {
                return getPaddingLeft();
            }

            @Override
            public int getPaddingBottom() {
                return getPaddingLeft();
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
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.refresh_head_background, R.color.refresh_head_text_color);
            return new ClassicsHeader(context).setArrowResource(R.drawable.vector_arrow_gray);
        });
    }

    private void initLog(boolean debug) {
        LogConfiguration config = new LogConfiguration
                .Builder()
                .logLevel(debug ? LogLevel.ALL : LogLevel.NONE)
                .tag("XLog")
                .build();
        String logPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/XLog/";
        Printer filePrinter = new FilePrinter
                .Builder(logPath)
                .fileNameGenerator(new LogFileNameGenerator(context.getPackageName()))
                .flattener(new LogFileFormat())
                .build();

        XLog.init(config, new AndroidPrinter(), filePrinter);
    }

    public Context getContext() {
        return context;
    }
}
