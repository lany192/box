package com.lany192.box.user.ui.nickname

import com.github.lany192.arch.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(/*private val repository: UserRepository*/) :
    LifecycleViewModel() {

    /**
     * 对昵称进行修改
     */
    fun modifyNickname(nickname: String) {
//        viewModelScope.launch {
//            requestWithLoading { repository.updateNickName(nickname) }
//                .collect {
//                    if (it.success()) {
//                        App.getUser().save(it.data)
//                        Toaster.show(it.msg)
//                    } else {
//                        StatusUtils.otherStatus(it.code, it.msg)
//                    }
//                }
//        }
    }
}