package com.github.lany192.arch;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.github.lany192.arch.utils.PhoneUtils;
import com.github.lany192.dialog.DialogHelper;
import com.github.lany192.kv.KVUtils;
import com.github.lany192.log.LogFileFormat;
import com.github.lany192.log.LogFileNameGenerator;
import com.github.lany192.utils.ContextUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

public class BoxApplication extends Application implements ViewModelStoreOwner {
    private final String TAG = "Box";
    private ViewModelStore mAppViewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.setApplicationContext(this);
        mAppViewModelStore = new ViewModelStore();
        KVUtils.get().init(this);
        ToastUtils.init(this);
        initLog();
        DialogHelper.get().init(this);
        initCatchException();
        initRefreshView();
        Log.i(TAG,"测试");
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    private void initCatchException() {
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> {
            XLog.tag(TAG).i("手机基本信息:" + PhoneUtils.getBaseInfo());
            XLog.tag(TAG).e(e.getLocalizedMessage());
            XLog.tag(TAG).enableStackTrace(10).e(TAG, "程序崩溃退出", e);
            Log.e(TAG, "程序崩溃退出", e);
        });
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
                .tag("XLog")
                .build();
        String logPath = getFilesDir().getPath() + "/log/";
        Log.i(TAG, "初始化日志文件路径:" + logPath);
        FilePrinter filePrinter = new FilePrinter
                .Builder(logPath)
                .fileNameGenerator(new LogFileNameGenerator())
                .flattener(new LogFileFormat())
                .build();
        XLog.init(config, new AndroidPrinter(), filePrinter);
    }
}
