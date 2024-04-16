package com.lany192.box.sample.ui.main.discover

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.ItemsFragment
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.decoration.Border
import com.github.lany192.decoration.Divider
import com.github.lany192.decoration.ItemDecoration
import com.lany192.box.sample.data.binder.CategoryBinder
import com.lany192.box.sample.data.binder.ImageBinder
import com.lany192.box.sample.databinding.FragmentDiscoverBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/discover")
class DiscoverFragment : ItemsFragment<DiscoverViewModel, FragmentDiscoverBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun getSpanCount(): Int {
        return 4
    }

    override fun getItemSpanSize(viewType: Int, position: Int): Int {
        return if (position < 4) {
            1
        } else 2
    }

    override fun getRefreshLayout(): SmartRefreshLayout {
        return binding.itemsView.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.itemsView.recyclerView
    }

    override fun init() {
        super.init()
        register(ImageBinder())
        register(CategoryBinder())
        binding.itemsView.addItemDecoration(object : ItemDecoration() {
            override fun getDivider(position: Int): Divider {
                val border = Border.builder().width(2f).build();
                return Divider(border, border, border, border)
            }
        })
    }
}