package com.github.lany192.box.sample.ui.login;

import com.github.lany192.box.mvvm.LifecycleViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends LifecycleViewModel {
    @Inject
    public LoginViewModel() {

    }
}
