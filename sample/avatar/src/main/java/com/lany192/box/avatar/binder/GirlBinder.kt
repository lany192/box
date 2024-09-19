package com.lany192.box.avatar.binder

import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.utils.ImageUtils
import com.lany192.box.avatar.databinding.ItemGirlBinding

class GirlBinder : ItemBinder<String, ItemGirlBinding>() {
    override fun convert(binding: ItemGirlBinding, item: String, position: Int) {
        ImageUtils.show(binding.image, item)
    }
}
