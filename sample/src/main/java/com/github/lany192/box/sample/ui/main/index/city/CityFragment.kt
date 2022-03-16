package com.github.lany192.box.sample.ui.main.index.city

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.PageFragment
import com.github.lany192.box.sample.data.binder.AreaBinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/fragment/city")
class CityFragment : PageFragment<CityViewModel>() {

    override fun getItemSpanSize(viewType: Int, position: Int): Int {
        return 1
    }

    override fun init() {
        super.init()
        register(AreaBinder())
    }
}