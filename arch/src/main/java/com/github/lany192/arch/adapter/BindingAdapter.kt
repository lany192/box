package com.github.lany192.arch.adapter

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.lany192.arch.binding.ViewBindingUtil.inflateWithGeneric

abstract class BindingAdapter<T, VB : ViewBinding> : BaseQuickAdapter<T, BindingHolder<VB>> {
    private lateinit var binding: VB

    private constructor() : super(0)

    private constructor(data: MutableList<T>) : super(0, data)

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        binding = inflateWithGeneric(this, parent)
        return BindingHolder(binding)
    }

    override fun convert(holder: BindingHolder<VB>, item: T) {
        itemBind(binding, item, holder.bindingAdapterPosition)
    }

    protected abstract fun itemBind(binding: VB, item: T, position: Int)
}