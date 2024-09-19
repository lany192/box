package com.lany192.box.avatar.ui.settings.about

import androidx.lifecycle.MutableLiveData
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : LifecycleViewModel() {
    private val items = MutableLiveData<List<String>>()

}