package com.github.lany192.box.sample.ui.main.index.city

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.databinding.FragmentPageBinding
import com.github.lany192.arch.items.ListFragment
import com.github.lany192.box.sample.data.binder.AreaBinder
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/fragment/city")
class CityFragment : ListFragment<CityViewModel, FragmentPageBinding>() {

    override fun getRefreshLayout(): SmartRefreshLayout {
        return binding.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }

    override fun getItemSpanSize(viewType: Int, position: Int): Int {
        return 1
    }

    override fun init() {
        super.init()
        register(AreaBinder())
    }
}