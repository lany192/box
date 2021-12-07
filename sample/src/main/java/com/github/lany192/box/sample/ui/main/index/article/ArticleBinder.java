package com.github.lany192.box.sample.ui.main.index.article;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.lany192.box.items.ItemBinder;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.bean.Article;
import com.github.lany192.box.sample.databinding.ItemArticleBinding;
import com.github.lany192.box.sample.ui.browser.BrowserActivity;
import com.github.lany192.box.utils.DateUtils;
import com.github.lany192.utils.ImageUtils;

import java.util.Date;

public class ArticleBinder extends ItemBinder<Article, ItemArticleBinding> {

    @Override
    public void convert(@NonNull BinderVBHolder<ItemArticleBinding> holder, Article article) {
        ImageUtils.show(holder.getViewBinding().image, MockUtils.getImageUrl());
        holder.getViewBinding().title.setText(article.getTitle());
        holder.getViewBinding().desc.setText(article.getAuthor());
        holder.getViewBinding().time.setText(DateUtils.format(new Date(article.getPublishTime())));
    }

    @NonNull
    @Override
    public ItemArticleBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        return ItemArticleBinding.inflate(layoutInflater, viewGroup, false);
    }

    @Override
    public void onClick(@NonNull BinderVBHolder<ItemArticleBinding> holder, @NonNull View view, Article item, int position) {
        Intent intent = new Intent(getContext(), BrowserActivity.class);
        intent.putExtra("url", item.getLink());
        getContext().startActivity(intent);
    }
}
