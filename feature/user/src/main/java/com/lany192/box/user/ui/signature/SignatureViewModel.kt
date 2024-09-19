package com.lany192.box.user.ui.signature

import com.github.lany192.arch.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignatureViewModel @Inject constructor() : LifecycleViewModel() {

    /**
     * 个性签名
     */
    var signature = ""

    /**
     * 提交修改
     */
    fun submit() {
//        viewModelScope.launch {
//            requestWithLoading { repository.updateSignature(signature) }
//                .collect {
//                    if (it.success()) {
//                        App.getUser().save(it.data)
//                        toast(it.msg)
//                    } else {
//                        StatusUtils.otherStatus(it.code, it.msg)
//                    }
//                }
//        }
    }
}