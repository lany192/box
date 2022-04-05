package com.lany192.box.sample.ui.main.index

import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.BoxFragment
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.databinding.FragmentIndexBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/index")
class IndexFragment : BoxFragment<IndexViewModel, FragmentIndexBinding>() {

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .navigationBarColor(android.R.color.holo_orange_light)
            .titleBar(binding.tabLayout)
            .init()
    }

    override fun init() {
        super.init()
        val items: MutableList<TabItem> = ArrayList()
        items.add(TabItem("推荐", AppRouter.getArticle()))
        items.add(TabItem("地区", AppRouter.getCity()))
        items.add(TabItem("图片", AppRouter.getGirl()))
        val adapter = TabAdapter(requireActivity(), items)
        binding.viewpager.adapter = adapter
        binding.tabLayout.setViewPager2(binding.viewpager, adapter.titles)
    }
}