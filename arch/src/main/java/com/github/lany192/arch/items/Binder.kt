package com.github.lany192.arch.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.binding.findClass
import com.github.lany192.arch.binding.getBinding

internal fun <T, VB : ViewBinding> BindingItemBinder<T, VB>.getBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
): VB {
    return findClass().getBinding(inflater, container)
}
