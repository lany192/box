package com.github.lany192.arch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.lany192.binding.getBinding
import java.lang.reflect.ParameterizedType

abstract class BindingAdapter<T, VB : ViewBinding> : BaseQuickAdapter<T, BindingHolder<VB>> {

    constructor(data: MutableList<T>) : super(0, data)

    private constructor(@LayoutRes layoutResId: Int) : super(layoutResId)

    private constructor(layoutResId: Int, data: MutableList<T>) : super(layoutResId, data)

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        val binding: VB = getClass<VB>(1).getBinding(LayoutInflater.from(parent.context), parent)
        return BindingHolder(binding)
    }

    override fun convert(holder: BindingHolder<VB>, item: T) {
        convert(holder.binding, item, holder.bindingAdapterPosition)
    }

    protected abstract fun convert(binding: VB, item: T, position: Int)

    /**
     * 获取第几个泛型的class
     */
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

}