package com.github.lany192.box.multitype

import androidx.recyclerview.widget.RecyclerView


abstract class ViewHolderInflater<T, VH : RecyclerView.ViewHolder> : ItemViewBinder<T, VH>()
