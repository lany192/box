package com.github.lany192.box.sample.ui.main.index.article;

import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Article;
import com.github.lany192.multitype.adapter.ItemViewHolder;
import com.github.lany192.multitype.delegate.ItemDelegate;

public class ArticleDelegate extends ItemDelegate<Article> {

    public ArticleDelegate(Article data) {
        super(data);
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_article;
    }

    @Override
    public void bind(ItemViewHolder holder, Article item, int position) {
        holder.setText(R.id.title, item.getTitle());
        holder.setImage(R.id.image, MockUtils.getImageUrl());
    }
}
