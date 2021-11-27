package com.github.lany192.box.multitype

import androidx.recyclerview.widget.RecyclerView


abstract class ViewHolderDelegate<T, VH : RecyclerView.ViewHolder> : ItemViewDelegate<T, VH>()
