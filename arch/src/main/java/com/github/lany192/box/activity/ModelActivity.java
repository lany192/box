package com.github.lany192.box.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.mvvm.BaseViewModel;

public class ModelActivity extends BasicActivity {

    protected <T extends BaseViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(this).get(modelClass);
    }
}
