package com.lany192.box.demo.binder

import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.utils.ImageUtils
import com.lany192.box.demo.databinding.ItemCategoryBinding
import com.lany192.box.network.data.bean.Category

class CategoryBinder : ItemBinder<Category, ItemCategoryBinding>() {
    override fun convert(binding: ItemCategoryBinding, item: Category, position: Int) {
        ImageUtils.show(binding.image, item.url)
        binding.title.text = item.name
    }
}
