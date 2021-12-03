package com.github.lany192.box.sample.ui.main.index.article;

import android.content.Intent;

import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Article;
import com.github.lany192.box.sample.ui.browser.BrowserActivity;
import com.github.lany192.box.utils.DateUtils;
import com.github.lany192.multitype.adapter.ItemViewHolder;
import com.github.lany192.multitype.delegate.ItemDelegate;

import java.util.Date;

public class ArticleDelegate extends ItemDelegate<Article> {

    public ArticleDelegate(Article data) {
        super(data);
    }

    @Override
    public int getSpanSize() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_article;
    }

    @Override
    public void bind(ItemViewHolder holder, Article item, int position) {
        holder.setText(R.id.title, item.getTitle());
        holder.setText(R.id.desc, item.getAuthor());
        holder.setText(R.id.time, DateUtils.format(new Date(item.getPublishTime())));
        holder.setImage(R.id.image, MockUtils.getImageUrl());
    }

    @Override
    public void onItemClicked(Article item, int position) {
        Intent intent = new Intent(getContext(), BrowserActivity.class);
        intent.putExtra("url", item.getLink());
        getContext().startActivity(intent);
    }
}
