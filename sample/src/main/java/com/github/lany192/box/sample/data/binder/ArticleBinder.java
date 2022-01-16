package com.github.lany192.box.sample.data.binder;

import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.AppRouter;
import com.github.lany192.arch.items.BindingItemBinder;
import com.github.lany192.arch.utils.DateUtils;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.data.bean.Article;
import com.github.lany192.box.sample.databinding.ItemArticleBinding;
import com.github.lany192.utils.ImageUtils;

import java.util.Date;

public class ArticleBinder extends BindingItemBinder<Article, ItemArticleBinding> {

    @Override
    public void onClick(@NonNull BindingHolder<ItemArticleBinding> holder, @NonNull View view, Article item, int position) {
        AppRouter.get().browser(item.getTitle(), item.getLink());
    }

    @Override
    public void convert(@NonNull BindingHolder<ItemArticleBinding> holder, Article item) {
        ImageUtils.show(holder.getBinding().image, MockUtils.getImageUrl());
        holder.getBinding().title.setText(item.getTitle());
        holder.getBinding().desc.setText(item.getAuthor());
        holder.getBinding().time.setText(DateUtils.format(new Date(item.getPublishTime())));
    }
}
