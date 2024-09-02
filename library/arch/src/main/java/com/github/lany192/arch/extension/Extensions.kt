package com.github.lany192.arch.extension

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hjq.toast.Toaster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

fun Any.toast(text: CharSequence) = Toaster.show(text)

fun Any.toast(@StringRes textId: Int) = Toaster.show(textId)

fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

val Context.screenWidthPx: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeightPx: Int
    get() = resources.displayMetrics.heightPixels

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

fun LifecycleOwner.postDelayedOnLifecycle(
    duration: Long,
    block: () -> Unit,
): Job = lifecycleScope.launch(Dispatchers.Main) {
    delay(duration)
    block()
}

fun View.postDelayedOnLifecycle(
    duration: Long,
    block: () -> Unit,
): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
    lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
        delay(duration)
        block()
    }
}

fun ViewModel.postDelayedOnLifecycle(
    duration: Long,
    block: () -> Unit,
): Job = viewModelScope.launch(Dispatchers.Main) {
    delay(duration)
    block()
}
