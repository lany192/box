package com.github.lany192.box.sample.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Fragment 生命周期
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    public FragmentLifecycle() {
        super();
    }

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentAttached(fm, f, context);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentAttached()");
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentCreated()");
        ARouter.getInstance().inject(f);
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentActivityCreated()");
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentViewCreated()");
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStarted(fm, f);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentStarted()");
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentResumed()");
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentPaused()");
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStopped(fm, f);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentStopped()");
    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentSaveInstanceState()");
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentViewDestroyed()");
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentDestroyed()");
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDetached(fm, f);
//        XLog.tag(f.getClass().getSimpleName()).i(" onFragmentDetached()");
    }
}
