package com.github.lany192.box.sample.di.component;

import com.github.lany192.box.sample.SampleApp;
import com.github.lany192.box.sample.di.module.ActivityModule;
import com.github.lany192.box.sample.di.module.HttpModule;

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
        HttpModule.class,
})
public interface AppComponent extends AndroidInjector<SampleApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleApp> {

    }
}