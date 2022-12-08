package com.github.lany192.arch;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.multidex.MultiDex;

import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.dialog.DialogHelper;
import com.github.lany192.log.LogUtils;
import com.github.lany192.utils.ContextUtils;
import com.github.lany192.utils.KVUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import xcrash.XCrash;

public abstract class BoxApplication extends Application implements ViewModelStoreOwner {
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

        LogUtils.init(this, debug());

        KVUtils.get().init(this);

        ToastUtils.init(this);
        ToastUtils.setView(R.layout.toast_view);
        ToastUtils.setDebugMode(debug());

        DialogHelper.get().init(this);

        DeviceId.get().grantedSDPermission();
        //处理异常
        CrashHelper.getInstance();

        initRefreshView();
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

    @Override
    public void onTerminate() {
        LogUtils.close();
        super.onTerminate();
    }

    public abstract boolean debug();
}
