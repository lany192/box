package com.lany192.box.sample.ui.main.index.article

import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.PageFragment
import com.github.lany192.utils.JsonUtils
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.data.binder.ArticleBinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/article")
class ArticleFragment : PageFragment<ArticleViewModel>() {

    init {
        register(ArticleBinder())
    }

    override fun init() {
        super.init()
        viewModel.sss.observe(this, Observer {
            ToastUtils.show(JsonUtils.object2json(it.data))
        })
    }
}