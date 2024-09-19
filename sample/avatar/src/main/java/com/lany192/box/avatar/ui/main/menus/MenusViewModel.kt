package com.lany192.box.avatar.ui.main.menus

import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.lany192.box.network.repository.BoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenusViewModel @Inject constructor(val repository: BoxRepository) :
    LifecycleViewModel() {

    override fun onLazyLoad() {
        super.onLazyLoad()
    }

}