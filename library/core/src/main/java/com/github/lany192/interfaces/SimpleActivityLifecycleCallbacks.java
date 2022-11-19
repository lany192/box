package com.github.lany192.interfaces;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface SimpleActivityLifecycleCallbacks extends Application.ActivityLifecycleCallbacks {

    @Override
    default void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    default void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    default void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    default void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    default void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    default void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    default void onActivityDestroyed(@NonNull Activity activity) {

    }
}
