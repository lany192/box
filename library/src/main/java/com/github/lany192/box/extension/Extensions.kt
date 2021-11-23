package com.github.lany192.box.extension

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * View拓展
 */
class Extensions {
    /**
     * Fragment是否存活
     */
    fun Fragment.isAlive(): Boolean {
        return this.activity != null
                && this.activity?.isDestroyed == false
                && this.activity?.isFinishing == false
                && this.isAdded
                && !this.isDetached
    }
    
    fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

    val Context.screenWidthPx: Int
        get() = resources.displayMetrics.widthPixels

    val Context.screenHeightPx: Int
        get() = resources.displayMetrics.heightPixels

    fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
        this?.let { Toast.makeText(it, text, duration).show() }

    fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
        this?.let { Toast.makeText(it, textId, duration).show() }

    fun Context.getCompatColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)

    fun Context.getCompatDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

    fun Context.getInteger(@IntegerRes id: Int) = resources.getInteger(id)

    fun Context.getBoolean(@BoolRes id: Int) = resources.getBoolean(id)

    fun TextView.setDrawableLeft(drawable: Int) {
        this.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
    }

    fun TextView.setDrawableTop(drawable: Int) {
        this.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0)
    }

    fun TextView.setDrawableRight(drawable: Int) {
        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    }

    fun TextView.setDrawableBottom(drawable: Int) {
        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawable)
    }
}