package com.lany192.box.sample;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.strictmode.FragmentStrictMode;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.arch.Box;
import com.github.lany192.utils.ContextUtils;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.lany192.box.router.lifecycle.ActivityLifecycle;
import com.lany192.box.sample.lancet.LancetTest;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

@HiltAndroidApp
public class SampleApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Box.getInstance().attachBaseContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return;
        }
        Box.getInstance().onCreate(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        initARouter();
        //注册Activity生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        // 处理RxJava返回的无人处理的错误
        RxJavaPlugins.setErrorHandler(new ConsumerAdapter<>());

        ShortcutUtils.init(this);
        LancetTest.test();
//        enableStrictMode();
        detectNonSdkApiUsageOnAndroidP();
    }

    private void detectNonSdkApiUsageOnAndroidP() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectNonSdkApiUsage();
        StrictMode.setVmPolicy(builder.build());
    }
    private void initARouter() {
        if (ContextUtils.isDebug()) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    private void enableStrictMode() {
        if (ContextUtils.isDebug()) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll() //所有违规操作
//                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
//                    .detectDiskReads() //磁盘读操作
//                    .detectDiskWrites() //磁盘写操作
//                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen() //API等级11
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll() //所有违规操作
//                    .detectLeakedSqlLiteObjects()//SQLite
//                    .detectLeakedClosableObjects() //API等级11
                    .penaltyLog()//在Logcat 中打印违规异常信息
                    .penaltyDeath()//违规则崩溃
                    .build());
            FragmentStrictMode.INSTANCE.setDefaultPolicy(new FragmentStrictMode.Policy.Builder()
                    .penaltyDeath()//违规则崩溃
                    .detectFragmentReuse()
                    .detectWrongFragmentContainer()
                    .detectRetainInstanceUsage()
                    .detectSetUserVisibleHint()
                    .detectFragmentTagUsage()
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .build());
        }
    }
}
