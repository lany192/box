package com.lany192.box.sample.ui.transformation.recycler

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Poster(
    val name: String,
    val release: String,
    val playtime: String,
    val description: String,
    val poster: String,
    val gif: String?,
) : Parcelable
