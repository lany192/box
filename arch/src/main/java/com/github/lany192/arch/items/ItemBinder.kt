package com.github.lany192.arch.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.BaseItemBinder
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.arch.adapter.BindingHolder
import com.github.lany192.binding.getBinding
import java.lang.reflect.ParameterizedType

abstract class ItemBinder<T, VB : ViewBinding> : BaseItemBinder<T, BindingHolder<VB>>() {
    @JvmField
    protected var log: Logger.Builder = XLog.tag(javaClass.name)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        return BindingHolder(getViewBinding(parent))
    }

    /**
     * 获取内容的ViewBinding实例。
     * 如果不想用反射，复写该方法，去反射。
     */
    open fun getViewBinding(parent: ViewGroup): VB {
        return getClass<VB>(1).getBinding(LayoutInflater.from(context), parent)
    }

    /**
     * 获取第几个泛型的class
     */
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

    override fun convert(holder: BindingHolder<VB>, data: T) {
        bind(holder.binding, data, holder.bindingAdapterPosition)
    }

    abstract fun bind(binding: VB, item: T, position: Int)

    fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }
}