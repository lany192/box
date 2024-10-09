package com.lany192.box.avatar.ui.splash

import com.github.lany192.arch.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : LifecycleViewModel() {
    private val _welcome = MutableSharedFlow<String>(replay = 1, extraBufferCapacity = 1)
    val welcome: SharedFlow<String> get() = _welcome

    init {
        _welcome.tryEmit("Hello Box")
    }
}