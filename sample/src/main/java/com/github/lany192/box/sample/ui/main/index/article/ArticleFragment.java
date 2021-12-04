package com.github.lany192.box.sample.ui.main.index.article;

import com.github.lany192.box.items.ItemsFragment;
import com.github.lany192.box.sample.bean.Article;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends ItemsFragment<ArticleViewModel> {
    {
        register(Article.class, new ArticleDelegate());
    }
}