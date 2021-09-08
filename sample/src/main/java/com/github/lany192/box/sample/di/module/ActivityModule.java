package com.github.lany192.box.sample.di.module;


import com.github.lany192.box.di.module.BaseModule;
import com.github.lany192.box.di.scope.ActivityScope;
import com.github.lany192.box.sample.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = BaseModule.class)
    abstract MainActivity main();
}
