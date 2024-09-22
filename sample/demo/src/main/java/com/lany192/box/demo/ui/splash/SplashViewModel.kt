package com.lany192.box.demo.ui.splash

import androidx.lifecycle.MutableLiveData
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : LifecycleViewModel() {
    val welcome = MutableLiveData<String>()

    init {
        welcome.postValue("Hello Box")
    }
}