package com.lany192.box.sample.ui.main.message

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.ViewModelFragment
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.github.lany192.arch.utils.BarUtils
import com.lany192.box.sample.databinding.FragmentMessageBinding
import com.lany192.box.sample.ui.main.index.city.CityBuilder
import com.lany192.box.sample.ui.main.index.girl.GirlBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/message")
class MessageFragment : ViewModelFragment<MessageViewModel, FragmentMessageBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this).titleBar(binding.toolbar).init()
    }

    override fun init() {
        super.init()
        val items = mutableListOf<TabItem>()
        items.add(TabItem("互动", GirlBuilder.getFragment()))
        items.add(TabItem("系统消息", CityBuilder.getFragment()))
        items.add(TabItem("游戏通知", GirlBuilder.getFragment()))
        val adapter = TabAdapter(requireActivity(), items)
        binding.viewpager.adapter = adapter
        binding.tabLayout.setViewPager(binding.viewpager, adapter.titles)
    }
}