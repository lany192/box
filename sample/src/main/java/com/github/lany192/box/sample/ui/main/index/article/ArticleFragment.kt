package com.github.lany192.box.sample.ui.main.index.article;

import com.github.lany192.arch.items.PageListFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends PageListFragment<ArticleViewModel> {
    {
        register(new ArticleBinder());
    }
}