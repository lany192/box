package com.github.lany192.extensions

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.github.lany192.utils.ImageUtils

@JvmSynthetic
fun ImageView.load(data: Any?) {
    ImageUtils.show(this, data)
}

@JvmSynthetic
fun ImageView.load(data: Any?, requestOptions: RequestOptions) {
    ImageUtils.show(this, data, requestOptions)
}