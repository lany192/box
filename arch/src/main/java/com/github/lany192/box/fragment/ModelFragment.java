package com.github.lany192.box.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.mvvm.LifecycleViewModel;

public abstract class ModelFragment extends BasicFragment {

    protected <T extends LifecycleViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(this).get(modelClass);
    }

    protected <T extends LifecycleViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(requireActivity()).get(modelClass);
    }
}
