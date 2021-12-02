package com.github.lany192.box.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.mvvm.BaseViewModel;

public abstract class ModelFragment extends BasicFragment {

    protected <T extends BaseViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(this).get(modelClass);
    }

    protected <T extends BaseViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(requireActivity()).get(modelClass);
    }
}
