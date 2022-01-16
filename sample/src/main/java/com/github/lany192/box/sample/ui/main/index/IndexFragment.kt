package com.github.lany192.box.sample.ui.main.index

import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.BindingFragment
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.github.lany192.box.sample.databinding.FragmentIndexBinding
import com.gyf.immersionbar.ImmersionBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
@Route(path = "/fragment/index")
class IndexFragment : BindingFragment<FragmentIndexBinding>() {
    private var viewModel: IndexViewModel? = null

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .navigationBarColor(android.R.color.holo_orange_light)
            .titleBar(binding.tabLayout)
            .init()
    }

    override fun init() {
        viewModel = getFragmentViewModel(IndexViewModel::class.java)
        val items: MutableList<TabItem> = ArrayList()
        items.add(TabItem("推荐", AppRouter.get().article))
        items.add(TabItem("地区", AppRouter.get().city))
        items.add(TabItem("图片", AppRouter.get().girl))
        val adapter = TabAdapter(requireActivity(), items)
        binding.viewpager.adapter = adapter
        binding.tabLayout.setViewPager2(binding.viewpager, adapter.titles)
    }
}