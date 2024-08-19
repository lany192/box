package com.github.lany192.extensions

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.github.lany192.utils.ImageUtils

fun ImageView.load(data: Any?, requestOptions: RequestOptions = RequestOptions()) {
    ImageUtils.show(this, data, requestOptions)
}