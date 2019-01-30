package com.lany.box.sample.dagger;

import android.app.Fragment;
import android.os.Bundle;

import com.lany.box.activity.BaseActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 如果使用了dagger，用这个基类
 */
public abstract class DaggerActivity extends BaseActivity implements HasFragmentInjector, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<androidx.fragment.app.Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> frameworkFragmentInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public AndroidInjector<androidx.fragment.app.Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return frameworkFragmentInjector;
    }
}
