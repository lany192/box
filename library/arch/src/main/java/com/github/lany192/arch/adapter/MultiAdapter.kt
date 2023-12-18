package com.github.lany192.arch.adapter

import android.content.Context
import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.BaseQuickAdapter
import com.github.lany192.arch.R
import java.lang.ref.WeakReference

/**
 * 多类型布局
 */
open class MultiAdapter(items: List<Any> = emptyList()) :
    BaseQuickAdapter<Any, RecyclerView.ViewHolder>(items) {
    private val mTypeMap = HashMap<Class<*>, Int>()

    private val typeViewHolders =
        SparseArray<OnBinderListener<Any, RecyclerView.ViewHolder>>(1)

    override fun onCreateViewHolder(
        context: Context, parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val listener = typeViewHolders.get(viewType)
            ?: throw IllegalArgumentException("ViewType: $viewType not found onViewHolderListener，please use addItemType() first!")

        return listener.onCreate(parent.context, parent, viewType).apply {
            itemView.setTag(R.id.BaseQuickAdapter_key_multi2, listener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: Any?) {
        findListener(holder)?.onBind(holder, position, item)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int, item: Any?, payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position, item)
            return
        }
        findListener(holder)?.onBind(holder, position, item, payloads)
    }

    fun <T : Any, VB : ViewBinding> addBinder(
        binder: ItemBinder<T, VB>
    ) = apply {
        val itemType = mTypeMap.size + 1
        binder.weakA = WeakReference(this)
        val clazz = binder.getClass<T>(0)
        mTypeMap[clazz] = itemType
        typeViewHolders.put(itemType, binder as OnBinderListener<Any, RecyclerView.ViewHolder>)
    }

    override fun getItemViewType(position: Int, list: List<Any>): Int {
        val clazz = list[position]::class.java
        return mTypeMap[clazz] ?: super.getItemViewType(position, list)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        findListener(holder)?.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        findListener(holder)?.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        findListener(holder)?.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return findListener(holder)?.onFailedToRecycleView(holder) ?: false
    }

    override fun isFullSpanItem(itemType: Int): Boolean {
        return super.isFullSpanItem(itemType) || (typeViewHolders.get(itemType)
            ?.isFullSpanItem(itemType) == true)
    }

    private fun findListener(holder: RecyclerView.ViewHolder): OnBinderListener<Any, RecyclerView.ViewHolder>? {
        return holder.itemView.getTag(R.id.BaseQuickAdapter_key_multi2) as? OnBinderListener<Any, RecyclerView.ViewHolder>
    }
}
