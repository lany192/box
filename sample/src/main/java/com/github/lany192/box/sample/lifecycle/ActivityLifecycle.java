package com.github.lany192.box.sample.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.elvishew.xlog.XLog;
import com.umeng.analytics.MobclickAgent;

/**
 * Activity生命周期
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private final FragmentLifecycle fragmentLifecycle = new FragmentLifecycle();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        //给Activity界面注入参数
        ARouter.getInstance().inject(activity);
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, false);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
//        XLog.tag(activity.getClass().getSimpleName()).i(" onActivityStarted()");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
//        XLog.tag(activity.getClass().getSimpleName()).i(" onActivityResumed()");
        MobclickAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
//        XLog.tag(activity.getClass().getSimpleName()).i(" onActivityPaused()");
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
//        XLog.tag(activity.getClass().getSimpleName()).i(" onActivityStopped()");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
//        XLog.tag(activity.getClass().getSimpleName()).i(" onActivitySaveInstanceState()");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        XLog.tag(activity.getClass().getSimpleName()).i(" onActivityDestroyed()");
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycle);
        }
    }
}
