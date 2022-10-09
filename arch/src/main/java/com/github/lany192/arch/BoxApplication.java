package com.github.lany192.arch;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.multidex.MultiDex;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.dialog.DialogHelper;
import com.github.lany192.log.LogFileFormat;
import com.github.lany192.log.LogFileNameGenerator;
import com.github.lany192.utils.ContextUtils;
import com.github.lany192.utils.KVUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import xcrash.XCrash;

public class BoxApplication extends Application implements ViewModelStoreOwner {
    private final String TAG = "Box";
    private ViewModelStore mAppViewModelStore;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        XCrash.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.setApplicationContext(this);
        mAppViewModelStore = new ViewModelStore();
        KVUtils.get().init(this);
        ToastUtils.init(this);
        initLog();
        DialogHelper.get().init(this);
        initRefreshView();
        DeviceId.get().grantedSDPermission();
        //处理异常
        CrashHelper.getInstance();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    private void initRefreshView() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(android.R.color.transparent, R.color.text_1level);//全局设置主题颜色
            return new ClassicsHeader(context)
                    .setEnableLastTime(false)
                    .setProgressResource(R.drawable.icon_loading_black)
                    .setArrowResource(R.drawable.vector_arrow_gray);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    private void initLog() {
        LogConfiguration config = new LogConfiguration
                .Builder()
                .logLevel(LogLevel.ALL)
                .jsonFormatter(data -> {
                    try {
                        return " \n" + new DefaultJsonFormatter().format(data);
                    } catch (Exception e) {
                        return " \n{\"code\":999999,\"msg\":\"json格式异常：" + e.getMessage() + "\"}";
                    }
                })
                .tag("XLog")
                .build();
        String logPath = getCacheDir().getPath() + "/log/";
        Log.i(TAG, "初始化日志文件路径:" + logPath);
        FilePrinter filePrinter = new FilePrinter
                .Builder(logPath)
                .fileNameGenerator(new LogFileNameGenerator())
                .flattener(new LogFileFormat())
                .build();
        XLog.init(config, new AndroidPrinter(), filePrinter);
    }
}
