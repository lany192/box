package com.lany192.box.avatar.binder

import com.alibaba.android.arouter.launcher.ARouter
import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.arch.utils.DateUtils
import com.github.lany192.extension.load
import com.lany192.box.avatar.MockUtils
import com.lany192.box.avatar.databinding.ItemArticleBinding
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
        binding.image.load(MockUtils.getImageUrl())
        binding.title.text = item.title + position
        binding.desc.text = item.author
        binding.time.text = DateUtils.format(Date(item.publishTime))
    }
}
