package com.github.lany192.box.sample.ui.main;

import com.github.lany192.box.mvvm.LifecycleViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends LifecycleViewModel {
    @Inject
    public MainViewModel() {

    }
}
