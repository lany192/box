package com.github.lany192.box.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.mvvm.LifecycleViewModel;

public abstract class ModelFragment extends BasicFragment {

    protected <T extends LifecycleViewModel> T getFragmentViewModel(@NonNull Class<T> modelClass) {
        T viewModel =  new ViewModelProvider(this).get(modelClass);
        getLifecycle().addObserver(viewModel);
        return viewModel;
    }

    protected <T extends LifecycleViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        T viewModel =  new ViewModelProvider(requireActivity()).get(modelClass);
        getLifecycle().addObserver(viewModel);
        return viewModel;
    }
}
