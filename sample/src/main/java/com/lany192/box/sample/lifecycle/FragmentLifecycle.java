package com.lany192.box.sample.lifecycle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.interfaces.SimpleFragmentLifecycleCallbacks;

/**
 * Fragment 生命周期
 */
public class FragmentLifecycle extends SimpleFragmentLifecycleCallbacks {

    public FragmentLifecycle() {
        super();
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        ARouter.getInstance().inject(f);
    }
}
