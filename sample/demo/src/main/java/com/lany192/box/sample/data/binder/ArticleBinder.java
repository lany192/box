package com.lany192.box.sample.data.binder;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.arch.utils.DateUtils;
import com.github.lany192.utils.ImageUtils;
import com.lany192.box.network.data.bean.Article;
import com.lany192.box.router.provider.BrowserProvider;
import com.lany192.box.sample.MockUtils;
import com.lany192.box.demo.databinding.ItemArticleBinding;

import java.util.Date;

import javax.inject.Inject;

public class ArticleBinder extends ItemBinder<Article, ItemArticleBinding> {
    @Inject
    public ArticleBinder() {
    }

    @Override
    public boolean isFullSpanItem(int itemType) {
        return true;
    }

    @Override
    public void onItemClick(@NonNull ItemArticleBinding holder, Article item, int position) {
        ARouter.getInstance().navigation(BrowserProvider.class).startBrowser(item.getTitle(), item.getLink());
    }

    @Override
    public void convert(@NonNull ItemArticleBinding binding, Article item, int position) {
        ImageUtils.show(binding.image, MockUtils.getImageUrl());
        binding.title.setText(item.getTitle() + position);
        binding.desc.setText(item.getAuthor());
        binding.time.setText(DateUtils.format(new Date(item.getPublishTime())));

        String ss = "本游戏是一款文字 生存 战斗养成独立游戏。" +
                "游戏<a href=\"http://www.qq.com\" target=\"_self\">链接跳转</a>难度较高，跪求新人选" +
                "择新手模式。本游戏讲述一个16岁离家出走少年在外的种种<br/>虐心遭遇。游戏无内购、无广告、无需联网哦";


//        binding.demo.setText(StringUtils.getHtml(ss));
    }

}
