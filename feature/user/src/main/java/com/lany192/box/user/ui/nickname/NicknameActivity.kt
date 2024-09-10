package com.lany192.box.user.ui.nickname


import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.arch.utils.BarUtils
import com.hjq.toast.Toaster
import com.lany192.box.user.UserHelper
import com.lany192.box.user.databinding.ActivityNicknameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/user/nickname", name = "昵称")
class NicknameActivity : ViewModelActivity<NicknameViewModel, ActivityNicknameBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.right.setOnClickListener {
            val nickname = binding.content.text.toString().trim()
            if (TextUtils.isEmpty(nickname)) {
                Toaster.show("昵称不能为空")
                return@setOnClickListener
            }
            viewModel.modifyNickname(nickname)
        }
        val nickname = UserHelper.getInstance().nickname
        if (!TextUtils.isEmpty(nickname)) {
            binding.content.setText(nickname)
            binding.content.setSelection(nickname.length)
        }
    }
}