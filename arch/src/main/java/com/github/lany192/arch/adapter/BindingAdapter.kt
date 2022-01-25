package com.github.lany192.arch.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.lany192.arch.binding.inflateWithGeneric

abstract class BindingAdapter<T, VB : ViewBinding> : BaseQuickAdapter<T, BindingHolder<VB>> {

    constructor(data: MutableList<T>) : super(0, data)

    private constructor(@LayoutRes layoutResId: Int) : super(layoutResId)

    private constructor(layoutResId: Int, data: MutableList<T>) : super(layoutResId, data)

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        val binding: VB = inflateWithGeneric(this, parent)
        return BindingHolder(binding)
    }

    override fun convert(holder: BindingHolder<VB>, item: T) {
        convert(holder.binding, item, holder.bindingAdapterPosition)
    }

    protected abstract fun convert(binding: VB, item: T, position: Int)
}