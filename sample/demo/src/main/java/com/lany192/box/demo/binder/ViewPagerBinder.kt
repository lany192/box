package com.lany192.box.demo.binder

import androidx.fragment.app.FragmentActivity
import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.lany192.box.demo.databinding.ItemViewPagerBinding
import com.lany192.box.demo.ui.main.index.article.ArticleBuilder
import com.lany192.box.network.data.bean.ViewPagerItem

class ViewPagerBinder : ItemBinder<ViewPagerItem, ItemViewPagerBinding>() {
    override fun isFullSpanItem(itemType: Int): Boolean {
        return true
    }

    override fun convert(binding: ItemViewPagerBinding, item: ViewPagerItem, position: Int) {
        val items: MutableList<TabItem> = ArrayList()
        items.add(TabItem("标题1", ArticleBuilder.getFragment()))
        items.add(TabItem("标题2", ArticleBuilder.getFragment()))
        val adapter = TabAdapter(
            (context as FragmentActivity?)!!, items
        )
        binding.viewPager.adapter = adapter
        binding.tabLayout.setViewPager(binding.viewPager, adapter.titles)
    }
}
