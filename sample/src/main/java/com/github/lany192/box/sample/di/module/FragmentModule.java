package com.github.lany192.box.sample.di.module;


import com.github.lany192.box.di.scope.FragmentScope;
import com.github.lany192.box.sample.fragment.city.CityFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract CityFragment city();
}
