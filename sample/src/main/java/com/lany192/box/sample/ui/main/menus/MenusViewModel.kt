package com.lany192.box.sample.ui.main.menus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.hjq.toast.Toaster
import com.lany192.box.network.data.bean.Area
import com.lany192.box.network.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenusViewModel @Inject constructor(val repository: BoxRepository) :
    LifecycleViewModel() {

    override fun onLazyLoad() {
        super.onLazyLoad()
    }

}