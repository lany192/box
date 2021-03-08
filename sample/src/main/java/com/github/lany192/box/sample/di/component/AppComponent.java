package com.github.lany192.box.sample.di.component;

import android.app.Application;

import com.github.lany192.box.sample.SampleApp;
import com.github.lany192.box.sample.di.module.FragmentModule;
import com.github.lany192.box.sample.di.module.HttpModule;
import com.github.lany192.dagger.ActivityModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class,
        HttpModule.class,
})
public interface AppComponent extends AndroidInjector<SampleApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}