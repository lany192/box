package com.github.lany192.box.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.mvvm.LifecycleViewModel;

public class ModelActivity extends BasicActivity {

    protected <T extends LifecycleViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(this).get(modelClass);
    }
}
