package com.github.lany192.arch.adapter

import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.viewholder.QuickViewHolder

class BindingHolder<VB : ViewBinding>(val binding: VB) : QuickViewHolder(binding.root)