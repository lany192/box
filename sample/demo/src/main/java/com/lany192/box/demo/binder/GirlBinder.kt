package com.lany192.box.demo.binder

import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.utils.ImageUtils
import com.lany192.box.demo.databinding.ItemGirlBinding

class GirlBinder : ItemBinder<String, ItemGirlBinding>() {
    override fun convert(binding: ItemGirlBinding, item: String, position: Int) {
        ImageUtils.show(binding.image, item)
    }
}
