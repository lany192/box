package com.github.lany192.arch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.github.lany192.binding.getBinding
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

abstract class ItemBinder<T : Any, VB : ViewBinding> : OnBinderListener<T, BindingHolder<VB>> {
    internal var weakA: WeakReference<MultiAdapter>? = null

    val adapter: MultiAdapter?
        get() = weakA?.get()

    val context: Context?
        get() = weakA?.get()?.context

    override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        return BindingHolder(getViewBinding(parent))
    }

    open fun getViewBinding(parent: ViewGroup): VB {
        return getClass<VB>(1).getBinding(LayoutInflater.from(context), parent)
    }

    override fun isFullSpanItem(itemType: Int): Boolean {
        return true
    }

    override fun onBind(holder: BindingHolder<VB>, position: Int, item: T?) {
        if (item == null) {
            return
        }
        convert(holder.binding, item, position)
        holder.itemView.setOnClickListener {
            onItemClick(holder, it, item, position)
        }
    }

    abstract fun convert(binding: VB, item: T, position: Int)

    open fun onItemClick(
        holder: BindingHolder<VB>,
        view: View,
        item: T?,
        position: Int
    ) {
    }

    /**
     * 获取第几个泛型的class，如果被继承，需要注意index的顺序
     */
    @Suppress("UNCHECKED_CAST")
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

    fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(context!!, colorResId)
    }
}