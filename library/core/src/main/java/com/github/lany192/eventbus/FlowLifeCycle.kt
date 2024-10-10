package com.github.lany192.eventbus

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun <reified T> getFlowCollectCount(event: Class<T>): Int =
    FlowBus.getInstance()
        .getEventObserverCount(event.name)

inline fun <reified T> getFlowCollectCountScope(scope: ViewModelStoreOwner, event: Class<T>) =
    FlowBus.getInstance().getEventObserverCount(event.name)

/**
 * repeatOnLifecycle
 */
fun <T> LifecycleOwner.launchWhenStateAtLeast(
    minState: Lifecycle.State,
    block: suspend CoroutineScope.() -> T
): Job = lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(minState) {
        block()
    }
}