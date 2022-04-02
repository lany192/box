package com.github.lany192.arch.items

import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.BaseItemBinder
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.arch.adapter.BindingHolder
import com.github.lany192.arch.binding.inflateWithGeneric
import java.lang.reflect.ParameterizedType

abstract class ItemBinder<T, VB : ViewBinding> : BaseItemBinder<T, BindingHolder<VB>>() {
    @JvmField
    protected var log: Logger.Builder = XLog.tag(javaClass.name)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        val binding: VB = inflateWithGeneric(this, parent)
        return BindingHolder(binding)
    }

    @Suppress("UNCHECKED_CAST")
    open fun getTargetClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    override fun convert(holder: BindingHolder<VB>, data: T) {
        bind(holder.binding, data, holder.bindingAdapterPosition)
    }

    abstract fun bind(binding: VB, item: T, position: Int)

    fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }
}