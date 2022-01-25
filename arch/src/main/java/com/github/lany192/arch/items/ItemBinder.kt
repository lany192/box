package com.github.lany192.arch.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.BaseItemBinder
import com.github.lany192.arch.adapter.BindingHolder
import com.github.lany192.arch.binding.findClass
import com.github.lany192.arch.binding.getBinding
import java.lang.reflect.ParameterizedType

abstract class ItemBinder<T, VB : ViewBinding> : BaseItemBinder<T, BindingHolder<VB>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        val binding: VB = findClass().getBinding(LayoutInflater.from(parent.context), parent)
        return BindingHolder(binding)
    }

    open fun getTargetClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    override fun convert(holder: BindingHolder<VB>, data: T) {
        bind(holder.binding, data, holder.bindingAdapterPosition)
    }

    abstract fun bind(binding: VB, data: T, position: Int)
}