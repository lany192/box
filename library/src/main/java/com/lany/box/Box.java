package com.lany.box;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.hjq.toast.ToastUtils;
import com.lany.box.event.NetWorkEvent;
import com.lany.box.utils.FileUtils;
import com.lany.box.utils.LogFileFormat;
import com.lany.box.utils.NetUtils;
import com.lany.box.widget.RefreshView;
import com.lany.sp.SPHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;

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
        ToastUtils.init(ctx);
        SPHelper.getInstance().init(ctx, debug);
        initLog(debug);
        initCatchException();
        initRefreshView();
        registerNetwork();
    }

    private void registerNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
            String ACTION_NAME = "android.net.conn.CONNECTIVTY_CHANGE";
            getContext().registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    EventBus.getDefault().post(new NetWorkEvent(NetUtils.isNetWorkAvailable()));
                }
            }, new IntentFilter(ACTION_NAME));
        }
    }

    private void initCatchException() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                XLog.tag(TAG).e(e.getLocalizedMessage());
                XLog.tag(TAG).st(10).e(TAG, "程序崩溃退出", e);
                Log.e(TAG, "程序崩溃退出", e);
            }
        });
    }

    private void initRefreshView() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.refresh_head_background, R.color.refresh_head_text_color);
                return new RefreshView(context).setArrowResource(R.drawable.vector_arrow_gray);
            }
        });
    }

    private void initLog(boolean debug) {
        LogConfiguration config = new LogConfiguration
                .Builder()
                .logLevel(debug ? LogLevel.ALL : LogLevel.NONE)
                .tag("XLog")
                .build();
        String logPath = FileUtils.getCacheDir(getContext()) + "/XLog/";
        Printer filePrinter = new FilePrinter
                .Builder(logPath)
                .fileNameGenerator(new DateFileNameGenerator())
                .logFlattener(new LogFileFormat())
                .build();

        XLog.init(config, new AndroidPrinter(), filePrinter);
    }

    public Context getContext() {
        return context;
    }
}
