package com.github.lany192.box.sample.ui.main.index.article;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.github.lany192.box.items.BaseViewHolder;
import com.github.lany192.box.items.ItemDelegate;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Article;
import com.github.lany192.box.utils.DateUtils;
import com.github.lany192.utils.ImageUtils;

import java.util.Date;

public class ArticleDelegate extends ItemDelegate<Article> {

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, Article item) {
        ImageView imageView = holder.getView(R.id.image);
        ImageUtils.show(imageView, MockUtils.getImageUrl());

        holder.setText(R.id.title, item.getTitle());
        holder.setText(R.id.desc, item.getAuthor());
        holder.setText(R.id.time, DateUtils.format(new Date(item.getPublishTime())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), BrowserActivity.class);
//                intent.putExtra("url", item.getLink());
//                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_article, parent, false);
        return new BaseViewHolder(view);
    }
}
