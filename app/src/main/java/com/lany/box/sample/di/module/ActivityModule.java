package com.lany.box.sample.di.module;


import com.lany.box.di.module.BaseModule;
import com.lany.box.di.scope.ActivityScope;
import com.lany.box.sample.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = BaseModule.class)
    abstract MainActivity main();
}
