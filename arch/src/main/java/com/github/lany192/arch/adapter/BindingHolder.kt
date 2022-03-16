package com.github.lany192.arch.adapter

import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class BindingHolder<VB : ViewBinding>(val binding: VB) : BaseViewHolder(binding.root)