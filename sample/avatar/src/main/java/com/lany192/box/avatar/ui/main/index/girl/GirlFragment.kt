package com.lany192.box.avatar.ui.main.index.girl

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.PageFragment
import com.lany192.box.avatar.data.binder.ArticleBinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/girl")
class GirlFragment : PageFragment<GirlViewModel>() {
    init {
        register(ArticleBinder())
    }
}