package com.github.lany192.box.sample.di.module;

import com.github.lany192.box.sample.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity main();
}
