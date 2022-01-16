package com.github.lany192.arch.binding

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.view.BindingView

internal fun <V : ViewBinding> BindingView<V>.getBinding(): V {
    return findClass().getBinding(LayoutInflater.from(context))
}