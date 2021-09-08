package com.github.lany192.box.di.module;

import java.util.Random;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class BaseModule {

    @Provides
    @Named("random")
    static int provideId() {
        return new Random().nextInt();
    }
}
