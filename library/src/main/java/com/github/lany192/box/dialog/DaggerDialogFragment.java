package com.github.lany192.box.dialog;

import android.content.Context;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

/**
 * 如果使用了dagger，用这个基类
 */
public abstract class DaggerDialogFragment extends DialogFragment implements HasAndroidInjector {
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }
}
