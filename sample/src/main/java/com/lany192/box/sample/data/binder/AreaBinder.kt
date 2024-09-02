package com.lany192.box.sample.data.binder

import com.github.lany192.arch.extension.load
import com.github.lany192.arch.items.ItemBinder
import com.github.lany192.dialog.SimpleDialog
import com.hjq.toast.Toaster
import com.lany192.box.network.data.bean.Area
import com.lany192.box.sample.MockUtils
import com.lany192.box.sample.databinding.ItemAreaBinding

class AreaBinder : ItemBinder<Area, ItemAreaBinding>() {

    override fun onItemClick(binding: ItemAreaBinding, item: Area, position: Int) {
        val count = if (item.subarea != null) item.subarea.size else 0
        val dialog = SimpleDialog()
        dialog.setTitle("提示")
        dialog.setMessage(item.name + count + "个地级市")
        dialog.setRightButton("确定") { Toaster.show(item.name) }
        dialog.setLeftButton("取消")
        dialog.show()
    }

    override fun convert(binding: ItemAreaBinding, item: Area, position: Int) {
        val count = if (item.subarea != null) item.subarea.size else 0
        binding.image.load(MockUtils.getImageUrl())
        binding.title.text = item.name
        binding.desc.text = "下辖" + count + "个区/市"
    }
}
