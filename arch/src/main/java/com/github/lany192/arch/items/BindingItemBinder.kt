package com.github.lany192.arch.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.BaseItemBinder
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class BindingItemBinder<T, VB : ViewBinding> : BaseItemBinder<T, BaseViewHolder>() {
    lateinit var binding: VB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = getBinding(LayoutInflater.from(parent.context), parent)
        return BaseViewHolder(binding.root)
    }
}