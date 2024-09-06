package com.github.lany192.arch.eventbus.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.github.lany192.arch.eventbus.EventBusInitializer

object ApplicationScopeViewModelProvider : ViewModelStoreOwner {
    private val eventViewModelStore: ViewModelStore = ViewModelStore()

    private val mApplicationProvider: ViewModelProvider by lazy {
        ViewModelProvider(
            ApplicationScopeViewModelProvider,
            ViewModelProvider.AndroidViewModelFactory.getInstance(EventBusInitializer.application)
        )
    }

    fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mApplicationProvider[modelClass]
    }

    override val viewModelStore: ViewModelStore
        get() = eventViewModelStore
}