package com.lany192.box.sample.ui.main.index

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.github.lany192.arch.utils.BarUtils
import com.lany192.box.sample.databinding.FragmentIndexBinding
import com.lany192.box.sample.ui.main.index.article.ArticleBuilder
import com.lany192.box.sample.ui.main.index.city.CityBuilder
import com.lany192.box.sample.ui.main.index.girl.GirlBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/index")
class IndexFragment : VMVBFragment<IndexViewModel, FragmentIndexBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this).titleBar(binding.tabLayout).init()
    }

    override fun init() {
        super.init()
//        //灰色模式
//        ViewUtils.setGrayStyle(binding.root, true)

        val items = mutableListOf<TabItem>()
        items.add(
            TabItem("推荐", ArticleBuilder.getFragment())
        )
        items.add(
            TabItem("地区", CityBuilder.getFragment())
        )
        items.add(
            TabItem("图片", GirlBuilder.getFragment())
        )
        val adapter = TabAdapter(requireActivity(), items)
        binding.viewpager.adapter = adapter
        binding.tabLayout.setViewPager(binding.viewpager, adapter.titles)
    }
}