package com.lany192.box.sample.ui.main.index

import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.lany192.box.sample.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(val repository: BoxRepository) :
    LifecycleViewModel() {
}