package com.github.lany192.arch.extension

import androidx.annotation.StringRes
import com.hjq.toast.Toaster

fun Any.toast(text: CharSequence) = Toaster.show(text)

fun Any.toast(@StringRes textId: Int) = Toaster.show(textId)
