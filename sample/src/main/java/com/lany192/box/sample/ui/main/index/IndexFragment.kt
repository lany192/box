package com.lany192.box.sample.ui.main.index

import com.alibaba.android.arouter.SampleRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.github.lany192.arch.utils.BarUtils
import com.lany192.box.sample.databinding.FragmentIndexBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/index")
class IndexFragment : VMVBFragment<IndexViewModel, FragmentIndexBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this)
            .titleBar(binding.tabLayout)
            .init()
    }

    override fun init() {
        super.init()
        val items = mutableListOf<TabItem>()
        items.add(TabItem("推荐", SampleRouter.getArticle()))
        items.add(TabItem("地区", SampleRouter.getCity()))
        items.add(TabItem("图片", SampleRouter.getGirl()))
        val adapter = TabAdapter(requireActivity(), items)
        binding.viewpager.adapter = adapter
        binding.tabLayout.setViewPager(binding.viewpager, adapter.titles)
    }
}