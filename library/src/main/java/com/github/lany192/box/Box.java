package com.github.lany192.box;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.github.lany192.box.utils.OtherUtils;
import com.github.lany192.box.utils.PhoneUtils;
import com.github.lany192.kv.KVUtils;
import com.github.lany192.log.LogFileFormat;
import com.github.lany192.log.LogFileNameGenerator;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

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
        KVUtils.get().init(ctx);
        ToastUtils.init(ctx);
        initLog(debug);
        initCatchException();
        initRefreshView();
        OtherUtils.closeAndroidPWarningDialog(debug);
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
        FilePrinter filePrinter = new FilePrinter
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
