package com.github.lany192.arch.binding

import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.activity.BindingActivity

internal fun <V : ViewBinding> BindingActivity<V>.getBinding(): V {
    return findClass().getBinding(layoutInflater)
}
