package com.github.lany192.arch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.BaseQuickAdapter
import com.github.lany192.binding.getBinding
import com.github.lany192.log.XLog
import java.lang.reflect.ParameterizedType

abstract class BindingAdapter<T : Any, VB : ViewBinding>(datas: List<T> = emptyList()) :
    BaseQuickAdapter<T, BindingHolder<VB>>(datas) {

    protected var log: XLog = XLog.tag(javaClass.simpleName)

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<VB> {
        return BindingHolder(getViewBinding(parent))
    }

    override fun onBindViewHolder(holder: BindingHolder<VB>, position: Int, item: T?) {
        item?.let {
            convert(holder.binding, it, position)
        }
        holder.binding.root.setOnClickListener {
            onItemClicked(holder.binding, item!!, holder.bindingAdapterPosition)
        }
    }

    protected abstract fun convert(binding: VB, item: T, position: Int)

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

    open fun onItemClicked(binding: VB, item: T, position: Int) {

    }
}