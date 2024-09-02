package com.github.lany192.arch.extension

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.github.lany192.utils.ImageUtils

fun ImageView.load(data: Any?) {
    ImageUtils.show(this, data)
}

fun ImageView.load(data: Any?, options: RequestOptions) {
    ImageUtils.show(this, data, options)
}