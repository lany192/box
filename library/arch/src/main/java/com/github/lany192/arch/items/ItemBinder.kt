package com.github.lany192.arch.items

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.adapter.BindingHolder
import com.github.lany192.arch.adapter.MultiAdapter
import com.github.lany192.arch.adapter.OnBinderListener
import com.github.lany192.binding.getBinding
import com.github.lany192.log.XLog
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

abstract class ItemBinder<T : Any, VB : ViewBinding> : OnBinderListener<T, BindingHolder<VB>> {
    @JvmField
    protected var log: XLog = XLog.tag(javaClass.simpleName)
    internal var weakA: WeakReference<MultiAdapter>? = null

    val adapter: MultiAdapter?
        get() = weakA?.get()

    val context: Context?
        get() = weakA?.get()?.context

    override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        return BindingHolder(getViewBinding(parent))
    }

    override fun onBind(holder: BindingHolder<VB>, position: Int, item: T?) {
        item?.let {
            convert(holder.binding, it, position)
        }
        holder.itemView.setOnClickListener {
            onItemClick(holder.binding, item!!, position)
        }
    }

    abstract fun convert(binding: VB, item: T, position: Int)

    open fun onItemClick(binding: VB, item: T, position: Int) {

    }

    open fun getViewBinding(parent: ViewGroup): VB {
        return getClass<VB>(1).getBinding(LayoutInflater.from(context), parent)
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