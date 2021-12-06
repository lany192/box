package com.github.lany192.box.sample.ui.main.index.article;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.items.ItemsFragment;
import com.github.lany192.box.sample.bean.Article;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends ItemsFragment<ArticleViewModel> {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter.register(Article.class, new ArticleDelegate());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}