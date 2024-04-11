package com.lany192.box.sample.ui.main.menus

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.utils.BarUtils
import com.lany192.box.sample.databinding.FragmentMenusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/menus")
class MenusFragment : VMVBFragment<MenusViewModel, FragmentMenusBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun init() {
        super.init()
        //
    }
}