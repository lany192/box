package com.github.lany192.arch;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.multidex.MultiDex;

import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.dialog.DialogHelper;
import com.github.lany192.log.LogUtils;
import com.github.lany192.utils.ContextUtils;
import com.hjq.toast.Toaster;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import xcrash.XCrash;

public class Box implements ViewModelStoreOwner {
    private ViewModelStore mAppViewModelStore;
    private volatile static Box instance = null;

    private Box() {
    }

    public static Box getInstance() {
        if (instance == null) {
            synchronized (Box.class) {
                if (instance == null) {
                    instance = new Box();
                }
            }
        }
        return instance;
    }

    public void attachBaseContext(Context context) {
        MultiDex.install(context);
        XCrash.init(context);
    }

    public void onCreate(Application application) {
//        ContextUtils.setApplicationContext(this);

        mAppViewModelStore = new ViewModelStore();

//        LogUtils.init(this, debug());

//        KVUtils.init(this);

        Toaster.init(application);
        Toaster.setView(R.layout.toast_view);
        Toaster.setDebugMode(ContextUtils.isDebug());

        DialogHelper.get().init(application);

        DeviceId.get().grantedSDPermission();
        //处理异常
//        CrashHelper.getInstance();

        initRefreshView();

        setWebViewDataDirectorySuffix();
    }

    private void setWebViewDataDirectorySuffix() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        WebView.setDataDirectorySuffix(Application.getProcessName());
    }


    @NonNull
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

    public void onTerminate() {
        LogUtils.close();
    }
}
