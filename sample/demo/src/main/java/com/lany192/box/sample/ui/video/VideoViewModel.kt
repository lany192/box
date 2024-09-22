package com.lany192.box.sample.ui.video

import androidx.lifecycle.MutableLiveData
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor() : LifecycleViewModel() {
    private val items = MutableLiveData<List<String>>()

}