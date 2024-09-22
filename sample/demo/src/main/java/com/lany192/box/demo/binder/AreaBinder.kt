package com.lany192.box.demo.binder

import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.extension.load
import com.github.lany192.extension.toast
import com.github.lany192.interfaces.OnSimpleListener
import com.lany192.box.network.data.bean.Area
import com.lany192.box.demo.MockUtils
import com.lany192.box.demo.databinding.ItemAreaBinding

class AreaBinder : ItemBinder<Area, ItemAreaBinding>() {

    override fun onItemClick(binding: ItemAreaBinding, item: Area, position: Int) {
        val count = if (item.subarea != null) item.subarea.size else 0
        SimpleDialog().apply {
            title = "我是标题"
            message = item.name + count + "个地级市"
            rightButton = "确定"
            leftButton = "取消"
            rightClickListener = OnSimpleListener { toast(item.name) }
        }.show()
    }

    override fun convert(binding: ItemAreaBinding, item: Area, position: Int) {
        val count = if (item.subarea != null) item.subarea.size else 0
        binding.image.load(MockUtils.getImageUrl())
        binding.title.text = item.name
        binding.desc.text = "下辖" + count + "个区/市"
    }
}
