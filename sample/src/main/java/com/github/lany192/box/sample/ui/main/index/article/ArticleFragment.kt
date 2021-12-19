package com.github.lany192.box.sample.ui.main.index.article

import com.github.lany192.arch.items.PageListFragment
import com.github.lany192.box.sample.data.binder.ArticleBinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : PageListFragment<ArticleViewModel>() {

    init {
        register(ArticleBinder())
    }
}