package com.lany.box.sample.di.component;

import com.lany.box.sample.SampleApp;
import com.lany.box.sample.di.module.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
})
public interface AppComponent extends AndroidInjector<SampleApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleApp> {

    }
}