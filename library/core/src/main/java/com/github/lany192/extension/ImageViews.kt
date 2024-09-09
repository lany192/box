package com.github.lany192.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.request.RequestOptions
import com.github.lany192.utils.ImageUtils

fun ImageView.load(data: Any?) {
    ImageUtils.show(this, data)
}

fun ImageView.load(data: Any?, @DrawableRes placeResId: Int) {
    ImageUtils.show(this, data, placeResId)
}

fun ImageView.load(data: Any?, options: RequestOptions) {
    ImageUtils.show(this, data, options)
}