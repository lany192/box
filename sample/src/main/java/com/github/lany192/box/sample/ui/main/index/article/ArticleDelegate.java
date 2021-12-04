package com.github.lany192.box.sample.ui.main.index.article;

import android.content.Intent;
import android.view.LayoutInflater;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Article;
import com.github.lany192.box.sample.databinding.ItemArticleBinding;
import com.github.lany192.box.sample.ui.browser.BrowserActivity;
import com.github.lany192.box.utils.DateUtils;
import com.github.lany192.multitype.delegate.ItemDelegate;
import com.github.lany192.utils.ImageUtils;

import java.util.Date;

public class ArticleDelegate extends ItemDelegate<Article, ItemArticleBinding> {

    public ArticleDelegate(Article data) {
        super(data);
    }

    @Override
    public int getSpanSize() {
        return 2;
    }

    @Override
    public ItemArticleBinding getViewBinding() {
        return ItemArticleBinding.inflate(LayoutInflater.from(getContext()));
    }

    @Override
    public void onBindItem(ItemArticleBinding binding, BaseViewHolder holder, Article item, int position) {
        holder.setText(R.id.title, item.getTitle());
        holder.setText(R.id.desc, item.getAuthor());
        holder.setText(R.id.time, DateUtils.format(new Date(item.getPublishTime())));
        ImageUtils.show(binding.image,MockUtils.getImageUrl());
    }

    @Override
    public void onItemClicked(Article item, int position) {
        Intent intent = new Intent(getContext(), BrowserActivity.class);
        intent.putExtra("url", item.getLink());
        getContext().startActivity(intent);
    }
}
