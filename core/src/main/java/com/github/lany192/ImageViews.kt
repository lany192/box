package com.github.lany192

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.github.lany192.utils.ImageUtils
import okhttp3.HttpUrl

import java.io.File

@JvmSynthetic
fun ImageView.load(uri: String?) {
    ImageUtils.show(this, uri)
}

@JvmSynthetic
fun ImageView.load(url: HttpUrl?) {
    ImageUtils.show(this, url)
}

@JvmSynthetic
fun ImageView.load(uri: Uri?) {
    ImageUtils.show(this, uri)
}

@JvmSynthetic
fun ImageView.load(file: File?) {
    ImageUtils.show(this, file)
}

@JvmSynthetic
fun ImageView.load(@DrawableRes drawableResId: Int) {
    ImageUtils.show(this, drawableResId)
}

@JvmSynthetic
fun ImageView.load(drawable: Drawable?) {
    ImageUtils.show(this, drawable)
}

@JvmSynthetic
fun ImageView.load(bitmap: Bitmap?) {
    ImageUtils.show(this, bitmap)
}

@JvmSynthetic
fun ImageView.load(data: Any?) {
    ImageUtils.show(this, data)
}