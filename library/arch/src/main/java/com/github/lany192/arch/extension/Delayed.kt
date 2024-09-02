package com.github.lany192.arch.extension

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
