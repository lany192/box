package com.lany192.box.sample.data.binder

import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.extensions.load
import com.lany192.box.sample.databinding.ItemImageBinding
import kotlinx.coroutines.launch

class ImageBinder : ItemBinder<String, ItemImageBinding>() {

    override fun convert(binding: ItemImageBinding, item: String, position: Int) {
        binding.image.load(item)
        lifecycleScope.launch {

        }
    }
}