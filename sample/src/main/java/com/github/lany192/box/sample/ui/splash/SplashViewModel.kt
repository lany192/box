package com.github.lany192.box.sample.ui.splash

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.github.lany192.arch.viewmodel.LifecycleViewModel

@HiltViewModel
class SplashViewModel @Inject constructor() : LifecycleViewModel() {
    val welcome = MutableLiveData<String>()

    init {
        welcome.postValue("Hello Box")
    }
}