package com.github.lany192.box.sample.ui.splash;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.box.mvvm.LifecycleViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SplashViewModel extends LifecycleViewModel {
    private final MutableLiveData<String> welcome = new MutableLiveData<>();

    @Inject
    public SplashViewModel() {
        welcome.postValue("Hello Box");
    }

    public MutableLiveData<String> getWelcome() {
        return welcome;
    }
}
