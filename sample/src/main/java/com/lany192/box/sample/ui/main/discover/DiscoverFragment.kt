package com.lany192.box.sample.ui.main.discover

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.ItemsFragment
import com.github.lany192.decoration.Divider
import com.github.lany192.decoration.ItemDecoration
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.data.binder.CategoryBinder
import com.lany192.box.sample.data.binder.ImageBinder
import com.lany192.box.sample.databinding.FragmentDiscoverBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/discover")
class DiscoverFragment : ItemsFragment<DiscoverViewModel, FragmentDiscoverBinding>() {

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .navigationBarColor(android.R.color.holo_red_light)
            .titleBar(binding.toolbar)
            .init()
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
                return Divider().setTopWidth(2f).setBottomWidth(2f).setLeftWidth(2f)
                    .setRightWidth(2f)
            }
        })
    }
}