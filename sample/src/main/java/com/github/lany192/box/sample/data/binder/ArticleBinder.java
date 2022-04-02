package com.github.lany192.box.sample.data.binder;

import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.AppRouter;
import com.github.lany192.arch.adapter.BindingHolder;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.arch.utils.DateUtils;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.data.bean.Article;
import com.github.lany192.box.sample.databinding.ItemArticleBinding;
import com.github.lany192.utils.ImageUtils;

import java.util.Date;

public class ArticleBinder extends ItemBinder<Article, ItemArticleBinding> {

    @Override
    public void onClick(@NonNull BindingHolder<ItemArticleBinding> holder, @NonNull View view, Article item, int position) {
        AppRouter.startBrowser(item.getTitle(), item.getLink());
    }

    @Override
    public void convert(@NonNull ItemArticleBinding binding, Article item, int position) {
        ImageUtils.show(binding.image, MockUtils.getImageUrl());
        binding.title.setText(item.getTitle());
        binding.desc.setText(item.getAuthor());
        binding.time.setText(DateUtils.format(new Date(item.getPublishTime())));
    }
}
