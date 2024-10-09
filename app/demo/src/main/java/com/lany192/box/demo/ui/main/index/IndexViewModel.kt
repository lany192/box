package com.lany192.box.demo.ui.main.index

import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.lany192.box.network.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(val repository: BoxRepository) :
    LifecycleViewModel() {
}