package com.lany192.box.router.lifecycle;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.interfaces.SimpleActivityLifecycleCallbacks;

/**
 * Activity生命周期
 */
public class ActivityLifecycle implements SimpleActivityLifecycleCallbacks {

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

    @CallSuper
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycle);
        }
    }
}
