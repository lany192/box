package com.github.lany192.box.sample.ui.about

import androidx.lifecycle.MutableLiveData
import com.github.lany192.box.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : LifecycleViewModel(){
    private val items = MutableLiveData<List<String>>()

}