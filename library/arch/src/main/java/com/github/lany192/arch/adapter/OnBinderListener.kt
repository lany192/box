package com.github.lany192.arch.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface OnBinderListener<T : Any, V : RecyclerView.ViewHolder> {

    fun onCreate(context: Context, parent: ViewGroup, viewType: Int): V

    fun onBind(holder: V, position: Int, item: T?)

    fun onBind(holder: V, position: Int, item: T?, payloads: List<Any>) {
        onBind(holder, position, item)
    }

    fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {}

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {}

    fun onViewRecycled(holder: RecyclerView.ViewHolder) {}

    fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean = false

    fun isFullSpanItem(itemType: Int): Boolean {
        return false
    }
}