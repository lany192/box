package com.github.lany192.box.sample.ui.main.index.article;

import android.content.Intent;
import android.view.LayoutInflater;

import com.github.lany192.box.sample.MockUtils;
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
    public ItemArticleBinding getViewBinding(LayoutInflater inflater) {
        return ItemArticleBinding.inflate(inflater);
    }

    @Override
    public void onBind(ItemArticleBinding binding, Article item, int position) {
        binding.title.setText(item.getTitle());
        binding.desc.setText(item.getAuthor());
        binding.time.setText(DateUtils.format(new Date(item.getPublishTime())));
        ImageUtils.show(binding.image, MockUtils.getImageUrl());
    }

    @Override
    public void onItemClicked(Article item, int position) {
        Intent intent = new Intent(getContext(), BrowserActivity.class);
        intent.putExtra("url", item.getLink());
        getContext().startActivity(intent);
    }
}
