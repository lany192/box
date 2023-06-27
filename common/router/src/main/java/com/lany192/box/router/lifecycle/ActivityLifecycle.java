package com.lany192.box.router.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Activity生命周期
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private final FragmentLifecycle fragmentLifecycle = new FragmentLifecycle();

    @CallSuper
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

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @CallSuper
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycle);
        }
    }
}
