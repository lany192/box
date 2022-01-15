package com.github.lany192.box.sample.ui.main.discover

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.ListFragment
import com.github.lany192.box.sample.data.binder.CategoryBinder
import com.github.lany192.box.sample.data.binder.ImageBinder
import com.github.lany192.box.sample.databinding.FragmentDiscoverBinding
import com.github.lany192.decoration.Divider
import com.github.lany192.decoration.ItemDecoration
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/fragment/discover")
class DiscoverFragment : ListFragment<DiscoverViewModel, FragmentDiscoverBinding>() {

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .navigationBarColor(android.R.color.holo_red_light)
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
        return binding.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }

    override fun initView() {
        super.initView()
        register(ImageBinder())
        register(CategoryBinder())
        binding.recyclerView.addItemDecoration(object : ItemDecoration() {
            override fun getDivider(position: Int): Divider {
                return Divider().setTopWidth(2f).setBottomWidth(2f).setLeftWidth(2f)
                    .setRightWidth(2f)
            }
        })
    }
}