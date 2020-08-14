package com.github.lany192.box.delegate

import android.content.Context
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.github.lany192.box.adapter.ItemViewHolder

interface Delegate : MultiItemEntity {
    /**
     * 返回布局文件id
     *
     * @return 布局文件id
     */
    @get:LayoutRes
    val layoutId: Int

    /**
     * 站位的大小,默认是2。如需修改复写该方法
     *
     * @return 大小
     */
    val spanSize: Int
        get() = 2

    fun convert(holder: ItemViewHolder?, context: Context?)
}