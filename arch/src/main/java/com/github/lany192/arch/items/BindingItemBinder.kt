package com.github.lany192.arch.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.BaseItemBinder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.lang.reflect.ParameterizedType

abstract class BindingItemBinder<T, VB : ViewBinding> :
    BaseItemBinder<T, BindingItemBinder.BindingHolder<VB>>() {

    class BindingHolder<VB : ViewBinding>(val binding: VB) : BaseViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        return BindingHolder(getBinding(LayoutInflater.from(parent.context), parent))
    }

    open fun getTargetClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }
}