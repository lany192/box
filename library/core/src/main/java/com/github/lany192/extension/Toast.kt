package com.github.lany192.extension

import androidx.annotation.StringRes
import com.hjq.toast.Toaster

fun Any.toast(text: String?) = text?.let {
    Toaster.show(it)
}

fun Any.toast(text: CharSequence?) = text?.let {
    Toaster.show(it)
}

fun Any.toast(@StringRes textId: Int) = Toaster.show(textId)
