package com.github.lany192.box.sample.ui.main.index.article;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.lany192.box.items.ItemDelegate;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.bean.Article;
import com.github.lany192.box.sample.databinding.ItemArticleBinding;
import com.github.lany192.box.utils.DateUtils;
import com.github.lany192.utils.ImageUtils;
import com.github.lany192.utils.JsonUtils;
import com.hjq.toast.ToastUtils;

import java.util.Date;

public class ArticleDelegate extends ItemDelegate<Article, ItemArticleBinding> {

    @Override
    public ItemArticleBinding getViewBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemArticleBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBind(ItemArticleBinding binding, Article item, int position) {
        ImageUtils.show(binding.image, MockUtils.getImageUrl());
        binding.title.setText(item.getTitle());
        binding.desc.setText(item.getAuthor());
        binding.time.setText(DateUtils.format(new Date(item.getPublishTime())));
    }

    @Override
    public void onItemClicked(Article item, int position) {
        ToastUtils.show(JsonUtils.object2json(item));
//        Intent intent = new Intent(getContext(), BrowserActivity.class);
//        intent.putExtra("url", item.getLink());
//        getContext().startActivity(intent);
    }
}
