package com.github.lany192.box.sample.ui.main.index.article;

import com.github.lany192.box.items.ItemsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends ItemsFragment<ArticleViewModel> {
    {
        register(new ArticleDelegate());
    }
}