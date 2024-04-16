package com.lany192.box.sample.ui.main.menus

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.utils.BarUtils
import com.lany192.box.sample.R
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
        val items = mutableListOf<MenuItem>()
        items.add(MenuItem("root检查", R.drawable.android))
        items.add(MenuItem("当前进程名称", R.drawable.android))
        items.add(MenuItem("加法hook", R.drawable.android))
        items.add(MenuItem("是否是模拟器", R.drawable.android))
        items.add(MenuItem("测试点1", R.drawable.android))
        items.add(MenuItem("测试点1", R.drawable.android))
        items.add(MenuItem("测试点1", R.drawable.android))
        binding.recyclerView.adapter = MenusAdapter(items)
    }
}