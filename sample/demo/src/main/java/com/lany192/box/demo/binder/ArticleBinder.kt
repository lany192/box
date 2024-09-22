package com.lany192.box.demo.binder

import com.alibaba.android.arouter.launcher.ARouter
import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.arch.utils.DateUtils
import com.github.lany192.utils.ImageUtils
import com.lany192.box.demo.MockUtils
import com.lany192.box.demo.databinding.ItemArticleBinding
import com.lany192.box.network.data.bean.Article
import com.lany192.box.router.provider.BrowserProvider
import java.util.Date
import javax.inject.Inject

class ArticleBinder @Inject constructor() : ItemBinder<Article, ItemArticleBinding>() {
    override fun isFullSpanItem(itemType: Int): Boolean {
        return true
    }

    override fun onItemClick(binding: ItemArticleBinding, item: Article, position: Int) {
        ARouter.getInstance().navigation(BrowserProvider::class.java)
            .startBrowser(item.title, item.link)
    }

    override fun convert(binding: ItemArticleBinding, item: Article, position: Int) {
        ImageUtils.show(binding.image, MockUtils.getImageUrl())
        binding.title.text = item.title + position
        binding.desc.text = item.author
        binding.time.text = DateUtils.format(Date(item.publishTime))

        val ss = "本游戏是一款文字 生存 战斗养成独立游戏。" +
                "游戏<a href=\"http://www.qq.com\" target=\"_self\">链接跳转</a>难度较高，跪求新人选" +
                "择新手模式。本游戏讲述一个16岁离家出走少年在外的种种<br/>虐心遭遇。游戏无内购、无广告、无需联网哦"


        //        binding.demo.setText(StringUtils.getHtml(ss));
    }
}
