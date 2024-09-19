package com.lany192.box.avatar.data.binder

import com.github.lany192.extension.load
import com.github.lany192.arch.items.ItemBinder
import com.lany192.box.avatar.databinding.ItemImageBinding

class ImageBinder : ItemBinder<String, ItemImageBinding>() {

    override fun convert(binding: ItemImageBinding, item: String, position: Int) {
        binding.image.load(item)
    }
}