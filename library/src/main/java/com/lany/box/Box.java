package com.lany.box;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.lany.box.utils.FileUtils;
import com.lany.box.utils.LogFileFormat;
import com.lany.box.widget.RefreshView;
import com.lany.sp.SPHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

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

    public void init(Context ctx) {
        init(ctx, false);
    }

    public void init(Context ctx, boolean debug) {
        Context app = ctx.getApplicationContext();
        if (app == null) {
            this.context = ctx;
        } else {
            this.context = ((Application) app).getBaseContext();
        }
        SPHelper.getInstance().init(ctx, debug);
        initLog(debug);
        initCatchException();
        initRefreshView();
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
