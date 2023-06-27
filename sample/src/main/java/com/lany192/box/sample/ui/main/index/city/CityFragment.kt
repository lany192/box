package com.lany192.box.sample.ui.main.index.city

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.PageFragment
import com.lany192.box.network.data.binder.AreaBinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/city")
class CityFragment : PageFragment<CityViewModel>() {

    override fun getItemSpanSize(viewType: Int, position: Int): Int {
        return 1
    }

    override fun init() {
        super.init()
        register(AreaBinder())
    }
}