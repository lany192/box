package com.github.lany192.box.sample.ui.main.index.article

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.databinding.FragmentPageBinding
import com.github.lany192.arch.items.ListFragment
import com.github.lany192.box.sample.data.binder.ArticleBinder
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/fragment/article")
class ArticleFragment : ListFragment<ArticleViewModel, FragmentPageBinding>() {

    init {
        register(ArticleBinder())
    }

    override fun getRefreshLayout(): SmartRefreshLayout {
        return binding.refreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }
}