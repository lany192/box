package com.lany192.box.sample.ui.main.message

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.BoxFragment
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.databinding.FragmentMessageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/message")
class MessageFragment : BoxFragment<MessageViewModel, FragmentMessageBinding>() {

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarColor(android.R.color.white)
            .navigationBarDarkIcon(true)
            .titleBar(binding.toolbar)
            .init()
    }

}