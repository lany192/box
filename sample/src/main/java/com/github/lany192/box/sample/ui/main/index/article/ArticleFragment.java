package com.github.lany192.box.sample.ui.main.index.article;

import com.github.lany192.box.items.PageFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends PageFragment<ArticleViewModel> {
    {
        register(new ArticleBinder());
    }
}