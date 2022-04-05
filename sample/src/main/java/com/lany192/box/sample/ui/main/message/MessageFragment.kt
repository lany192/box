package com.lany192.box.sample.ui.main.message

import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.BoxFragment
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
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

    override fun init() {
        super.init()
        val items: MutableList<TabItem> = ArrayList()
        items.add(TabItem("互动", AppRouter.getArticle()))
        items.add(TabItem("系统消息", AppRouter.getCity()))
        items.add(TabItem("游戏通知", AppRouter.getGirl()))
        val adapter = TabAdapter(requireActivity(), items)
        binding.viewpager.adapter = adapter
        binding.tabLayout.setViewPager2(binding.viewpager, adapter.titles)
    }
}