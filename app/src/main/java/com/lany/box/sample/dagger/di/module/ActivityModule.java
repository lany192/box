package com.lany.box.sample.dagger.di.module;


import com.lany.box.sample.MainActivity;
import com.lany.box.sample.dagger.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = BaseModule.class)
    abstract MainActivity main();
}
